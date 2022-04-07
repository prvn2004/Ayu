package com.project.ayu

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.databinding.FragmentDoctorAppointmentsBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class Doctor_Appointments : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentDoctorAppointmentsBinding
    lateinit var LinkModel: ArrayList<MyPatientDataFile>
    private var JsonFile =
        "http://ayubackend.herokuapp.com/api/appointment/list"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        LinkModel = arrayListOf<MyPatientDataFile>()
        binding = FragmentDoctorAppointmentsBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemList = ArrayList<MyPatientDataFile>()

        binding.progressBar.visibility = View.VISIBLE
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, JsonFile, Response.Listener { response ->
                binding.progressBar.visibility = View.GONE


                try {
                    val jsonArray = JSONArray(response)
                    Log.d("here", response)
                    for (i in 0..jsonArray.length() - 1) {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val doc_ph_number = jsonObject.getString("ph_no_doctor")
                        val patient_ph_number = jsonObject.getString("ph_no_patient")
                        val date = jsonObject.getString("date")
                        val time = jsonObject.getString("time")
                        val id = jsonObject.getString("_id")

                        Toast.makeText(activity, date, Toast.LENGTH_SHORT).show()

                        itemList.add(
                            MyPatientDataFile(
                                "My no.: $patient_ph_number",
                                "+91$doc_ph_number",
                                time,
                                "Date: $date",
                                id
                            )
                        )

                    }
                } catch (e: Exception) {
                    binding.progressBar.visibility = View.GONE

                    e.printStackTrace()
                }


                var adaptor = MyPatientAdapter(itemList)
                binding.recyclerView.adapter = adaptor


            }, Response.ErrorListener { error ->
                binding.progressBar.visibility = View.GONE

                Toast.makeText(
                    activity,
                    "$error", Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params1: HashMap<String, String> = HashMap()
                return params1
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                val authToken = preferences.getString("authtoken", "nothing").toString()
                val params: MutableMap<String, String> = HashMap()
                params["auth-token"] = authToken
                return params
            }
        }
        val rq: RequestQueue = Volley.newRequestQueue(activity)
        rq.add(stringRequest)


        return binding.root
    }


}