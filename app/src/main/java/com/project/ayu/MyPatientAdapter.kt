package com.project.ayu;

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.databinding.MypatientListItemBinding
import org.json.JSONObject

class MyPatientAdapter(val LinkList: ArrayList<MyPatientDataFile>) :
    RecyclerView.Adapter<MyPatientAdapter.MyViewHolder>() {

    lateinit var binding: MypatientListItemBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            MypatientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding) //returning MyViewHolder class with a view inside it
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link3 = LinkList[position]
        holder.friction(Link3, position, LinkList)

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
                        Toast.makeText(it.context, "Cancelled succesfully", Toast.LENGTH_SHORT)
                            .show()
                        if (progressDialog.isShowing) progressDialog.dismiss()
                        binding.TimeButton1.text = "cancelled"
                        binding.doctorCancelButton.isClickable = false
                        binding.doctorCancelButton.isEnabled = false
                    }catch (e: Exception) {

                        e.printStackTrace()
                        if (progressDialog.isShowing) progressDialog.dismiss()

                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(it.context, "Error in cancelling", Toast.LENGTH_SHORT).show()
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

        binding.PatientNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + LinkList.get(position).getPatient()))
            it.context.startActivity(intent)
        }

    }
//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(ItemViewBinding: MypatientListItemBinding) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {


        private val binding = ItemViewBinding
        fun friction(Link: MyPatientDataFile, position: Int, list: ArrayList<MyPatientDataFile>) {
            val context = itemView.getContext();
            binding.DoctorNumber.text = Link.getDoctor().toString()
            binding.PatientNumber.text = Link.getPatient().toString()
            binding.AppointmentDate.text = Link.getDate().toString()
            binding.TimeButton1.text = Link.getTime().toString()
        }
    }


//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

    interface OnItemClickListener {
        fun onItemClicked(Link: DoctorDataFile)
    }
}
