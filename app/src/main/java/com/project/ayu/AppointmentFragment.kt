package com.project.ayu

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.databinding.FragmentAppointmentBinding
import org.json.JSONArray
import org.json.JSONObject


class
AppointmentFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentAppointmentBinding
    private lateinit var LinkModel: ArrayList<DoctorDataFile>
    private var FetchDoctors =
        "http://ayubackend.herokuapp.com/api/doctor/fetchalldoctors"

    override fun onCreate(savedInstanceState: Bundle?

    ) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        LinkModel = arrayListOf<DoctorDataFile>()

        binding = FragmentAppointmentBinding.inflate(layoutInflater)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemList = ArrayList<DoctorDataFile>()

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val newAuthToken = preferences.getString("authtoken", "failed").toString()
        Log.d("here1", newAuthToken)

        binding.progressBar.visibility = View.VISIBLE
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, FetchDoctors, Response.Listener<String> { response ->
                binding.progressBar.visibility = View.GONE

                val jsonArray = JSONArray(response)
                Log.d("here", response)
                try {
                    for (i in 0..jsonArray.length() - 1) {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("name")
                        val ph_number = jsonObject.getString("ph_number")
                        val field_of_specialization =
                            jsonObject.getString("field_of_specialization")
                        val reg_no = jsonObject.getString("reg_no")
                        val years_of_exp = jsonObject.getString("years_of_exp")

                        itemList.add(
                            DoctorDataFile(
                                name,
                                ph_number,
                                R.drawable.doctoravatarmale,
                                field_of_specialization,
                                "Experience: $years_of_exp yrs",
                                reg_no
                            )
                        )

                    }
                } catch (e: Exception) {
                    binding.progressBar.visibility = View.GONE

                    e.printStackTrace()
                }

                val adaptor = MyAdapter(itemList)
                binding.recyclerView.adapter = adaptor


            }, Response.ErrorListener { error ->
                binding.progressBar.visibility = View.GONE


            }) {
            override fun getParams(): Map<String, String> {

                val params1: HashMap<String, String> = HashMap()
//                params1["Content-Type"] = "application/json"
                return params1
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["auth-token"] = newAuthToken
                return params
            }

        }
        val rq = Volley.newRequestQueue(activity)
        rq.add(stringRequest)

        recyclerView.adapter = MyAdapter(itemList)

        return binding.root
    }



}