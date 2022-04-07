package com.project.ayu

import android.app.ProgressDialog
import android.content.ClipData.Item
import android.content.Intent
import android.net.Uri
import android.preference.PreferenceManager
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewTreeLifecycleOwner.get
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.databinding.DoctorListItemsBinding
import com.project.ayu.databinding.PastAppointmentListItemBinding
import org.json.JSONObject
import java.lang.reflect.Array.get
import java.nio.file.Paths.get
import java.util.HashMap
import kotlin.contracts.contract
import kotlin.coroutines.coroutineContext


class PastAppointmentAdapter(val LinkList: ArrayList<PastAppointmentsDataFile>) :
    RecyclerView.Adapter<PastAppointmentAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: PastAppointmentListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            PastAppointmentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(binding) //returning MyViewHolder class with a view inside it
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link2 = LinkList[position]
        holder.friction(Link2, position, LinkList)

        binding.doctorCancelButton.setOnClickListener {
            val progressDialog = ProgressDialog(it.context)
            progressDialog.setMessage("Cancelling appointment....")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val id = LinkList.get(position).getId().toString()
            val stringRequest: StringRequest = object : StringRequest(
                Method.DELETE,
                "http://ayubackend.herokuapp.com/api/appointment/cancelAppointment/$id",
                Response.Listener { response ->

                    try {
                        val jsonObject = JSONObject(response)
                        Log.d("hello", "cancelItem: $response")
                        binding.TimeButton1.text = "cancelled"
                        binding.doctorCancelButton.isClickable = false
                        binding.doctorCancelButton.isEnabled = false

                    } catch (e: Exception) {

                        e.printStackTrace()
                        if (progressDialog.isShowing) progressDialog.dismiss()

                    }
                    if (progressDialog.isShowing) progressDialog.dismiss()



                },
                Response.ErrorListener { error ->
                    if (progressDialog.isShowing) progressDialog.dismiss()

                }) {
                override fun getParams(): Map<String, String> {
                    val params1: HashMap<String, String> = HashMap()
                    return params1
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(it.context)
                    val authToken = preferences.getString("authtoken", "nothing").toString()
                    val params: MutableMap<String, String> = HashMap()
                    params["auth-token"] = authToken
                    return params
                }
            }
            val rq: RequestQueue = Volley.newRequestQueue(it.context)
            rq.add(stringRequest)
        }
        binding.doctorNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + LinkList.get(position).getNumber()))
            it.context.startActivity(intent)
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(ItemViewBinding: PastAppointmentListItemBinding) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {


        private val binding = ItemViewBinding
        fun friction(
            Link: PastAppointmentsDataFile,
            position: Int,
            list: ArrayList<PastAppointmentsDataFile>
        ) {
            val context = itemView.getContext();
            binding.DoctorImage.setImageResource(list[position].DoctorImage)
            binding.doctorDate.text = Link.getDate()
            binding.doctorNumber.text = Link.getNumber().toString()
            binding.TimeButton1.text = Link.getTime().toString()
            binding.id.text = Link.getId().toString()
        }
    }


//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

    interface OnItemClickListener {
        fun onItemClicked(Link: DoctorDataFile)
    }

}