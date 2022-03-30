package com.project.ayu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.databinding.FragmentPatientViewingDoctorProfileBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class Patient_viewing_doctor_profile : Fragment() {
    lateinit var binding: FragmentPatientViewingDoctorProfileBinding
    private var Register = "http://ayubackend.herokuapp.com/api/appointment/schedule"

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} am"
                        } else {
                            "${hourOfDay + 12}:${minute} am"
                        }
                    }
                    hourOfDay > 12 -> {
                        if (minute < 10) {
                            "${hourOfDay - 12}:0${minute} pm"
                        } else {
                            "${hourOfDay - 12}:${minute} pm"
                        }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} pm"
                        } else {
                            "${hourOfDay}:${minute} pm"
                        }
                    }
                    else -> {
                        if (minute < 10) {
                            "${hourOfDay}:${minute} am"
                        } else {
                            "${hourOfDay}:${minute} am"
                        }
                    }
                }


                binding.TimeText.text = formattedTime
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val simpleDateFormatArrivals = SimpleDateFormat("HH:mm", Locale.UK)

        binding = FragmentPatientViewingDoctorProfileBinding.inflate(inflater, container, false)
        binding.DateText.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { datePicker, year, month, day ->
                    if (month < 10) {
                        binding.DateText.setText(year.toString() + "-" + "0" + (month + 1) + "-" + day.toString())
                    } else {
                        binding.DateText.setText(year.toString() + "-" + (month + 1) + "-" + day.toString())
                    }
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            datePickerDialog.show()
        })
        binding.TimeText.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                // pass the Context
                activity,
                // listener to perform task
                // when time is picked
                timePickerDialogListener,
                // default hour when the time picker
                // dialog is opened
                12,
                // default minute when the time picker
                // dialog is opened
                10,
                // 24 hours time picker is
                // false (varies according to the region)
                true
            )

            // then after building the timepicker
            // dialog show the dialog to user
            timePicker.show()
        }

        binding.BookDoctorFinal.setOnClickListener {

            val ph_doc = binding.doctorNumber2.toString().trim()
            val date = binding.DateText.text.toString().trim()
            val Time = binding.TimeText.text.toString().trim()

            if (ph_doc.isEmpty() || Time.isEmpty() || date.isEmpty()) {
                Toast.makeText(
                    activity,
                    "please enter valid data",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                BookAppointment()
                Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show()
            }
        }
        val value = requireArguments().getString("YourKey")
        Toast.makeText(activity, "value", Toast.LENGTH_SHORT).show()

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val authToken = preferences.getString("authtoken", "nothing").toString()
//        Toast.makeText(activity, authToken, Toast.LENGTH_SHORT).show()


        return binding.root
    }

    private fun BookAppointment() {
        binding.progressBar.visibility = View.VISIBLE
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Register,
            Response.Listener { response ->
                binding.progressBar.visibility = View.GONE
                try {
                    Log.d("look here praveen", response)
                    val jsonObject: JSONObject = JSONObject(response)
                    val success = jsonObject.getString("date").toString()
                    if (success.length > 0) {
                        Toast.makeText(activity, "Booked date is $success", Toast.LENGTH_LONG)
                            .show()
                    }

                } catch (e: Exception) {
                    binding.progressBar.visibility = View.GONE
                    e.printStackTrace()
                    Toast.makeText(
                        activity,
                        "$e", Toast.LENGTH_LONG
                    ).show()
                }
            }, Response.ErrorListener { error ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    activity,
                    "$error", Toast.LENGTH_LONG
                ).show()
            }) {

            override fun getParams(): HashMap<String, String> {


                val ph_doc = binding.doctorNumber2.toString().trim()
                val date = binding.DateText.text.toString().trim()
                val Time = binding.TimeText.text.toString().trim()

                val params1: HashMap<String, String> = HashMap()
//                params1["Content-Type"] = "application/json"
                params1["ph_doc"] = "3939393939"
                params1["date"] = "2020-03-19"
                params1["time"] = "18:18"

                return params1
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                val authToken = preferences.getString("authtoken", "nothing").toString()
                val params: MutableMap<String, String> = HashMap()
                params["auth-token"] = authToken
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
//        Toast.makeText(activity, "request code run", Toast.LENGTH_SHORT).show()
        requestQueue.add(stringRequest)
    }
}