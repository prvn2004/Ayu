package com.project.ayu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.databinding.FragmentPatientViewingDoctorProfileBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class Patient_viewing_doctor_profile : AppCompatActivity() {
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
                            "0${hourOfDay + 12}:0${minute}"
                        } else {
                            "0${hourOfDay + 12}:${minute}"
                        }
                    }
                    hourOfDay > 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute}"
                        } else {
                            "${hourOfDay}:${minute}"
                        }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute}"
                        } else {
                            "${hourOfDay}:${minute}"
                        }
                    }
                    hourOfDay <10 -> {
                        if (minute < 10) {
                            "0${hourOfDay}:0${minute}"
                        } else {
                            "0${hourOfDay}:${minute}"
                        }
                    }
                    else -> {
                        if (minute < 10) {
                            "${hourOfDay}:${minute}"
                        } else {
                            "${hourOfDay}:${minute}"
                        }
                    }
                }


                binding.TimeText.text = formattedTime
            }
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        binding = FragmentPatientViewingDoctorProfileBinding.inflate(layoutInflater)
        val simpleDateFormatArrivals = SimpleDateFormat("HH:mm", Locale.UK)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.DateText.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { datePicker, year, month, day ->
                    when{
                        day<10 && month<10 -> binding.DateText.setText(year.toString() + "-" + "0" + (month + 1) + "-" + "0" + day.toString())
                        day<10 && month>=10 -> binding.DateText.setText(year.toString() + "-" + (month + 1) + "-" + "0" + day.toString())
                        day>=10 && month<10 -> binding.DateText.setText(year.toString() + "-" + "0" + (month + 1) + "-" + day.toString())
                        day>=10 && month>=10 ->binding.DateText.setText(year.toString() + "-" + (month + 1) + "-" +  day.toString())
                    }
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            datePickerDialog.show()
        })
        val DoctorName = intent.getStringExtra("DoctorName").toString()
        val DoctorExpertise = intent.getStringExtra("DoctorExpertise").toString()
        val DoctorExperience = intent.getStringExtra("DoctorExperience").toString()
        val DoctorNumber = intent.getStringExtra("DoctorNumber").toString()
        val DoctorReg = intent.getStringExtra("DoctorReg").toString()

        binding.doctorName.text = DoctorName
        binding.doctorNumber2.text = DoctorNumber
        binding.doctorExpertiseText3.text = DoctorExpertise
        binding.doctorRegNo2.text = DoctorReg
        binding.doctorExperienceYear2.text = DoctorExperience


        binding.close.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.TimeText.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                // pass the Context
                this,
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
            Log.d("restartt", "$date")
            if (ph_doc.isEmpty() || Time.isEmpty() || date.isEmpty()) {
                Toast.makeText(
                    this,
                    "please enter valid data",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                BookAppointment()

            }
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val authToken = preferences.getString("authtoken", "nothing").toString()
//        Toast.makeText(activity, authToken, Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this, "Booked date is $success", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } catch (e: Exception) {
                    binding.progressBar.visibility = View.GONE
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        "$e", Toast.LENGTH_LONG
                    ).show()
                }
            }, Response.ErrorListener { error ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    this,
                    "$error", Toast.LENGTH_LONG
                ).show()
            }) {

            override fun getParams(): HashMap<String, String> {


                val ph_doc = binding.doctorNumber2.text.toString().trim()
                val date = binding.DateText.text.toString().trim()

                val Time = binding.TimeText.text.toString().trim()

                val params1: HashMap<String, String> = HashMap()
//                params1["Content-Type"] = "application/json"
                params1["ph_doc"] = ph_doc
                params1["date"] = date
                params1["time"] = Time

                return params1
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val preferences =
                    PreferenceManager.getDefaultSharedPreferences(this@Patient_viewing_doctor_profile)
                val authToken = preferences.getString("authtoken", "nothing").toString()
                val params: MutableMap<String, String> = HashMap()
                params["auth-token"] = authToken
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
//        Toast.makeText(activity, "request code run", Toast.LENGTH_SHORT).show()
        requestQueue.add(stringRequest)
    }
}