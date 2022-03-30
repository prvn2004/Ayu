package com.project.ayu.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.R
import com.project.ayu.databinding.FragmentDoctorSignUpBinding
import org.json.JSONObject

class DoctorSignUp : Fragment() {
    private var SignUpUrl = "http://ayubackend.herokuapp.com/api/auth/docsignup"
    private lateinit var binding: FragmentDoctorSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signUpDoctor.setOnClickListener {
            val Name = binding.UserName.text.toString().trim()
            val Email = binding.mailaddress.text.toString().trim()
            val Password = binding.password.text.toString().trim()
            val MobileNo = binding.password.text.toString()
            val Gender = binding.Gender.text.toString().trim()
            val Age = binding.Age.text.toString().trim()
            val experience = binding.Experience.text.toString().trim()
            val specialisation = binding.speacialisation.text.toString().trim()
            val regno = binding.regNo.text.toString().trim()
            if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty() || MobileNo.isEmpty() || Gender.isEmpty() || Age.isEmpty()|| experience.isEmpty()|| specialisation.isEmpty()|| regno.isEmpty()) {
                Toast.makeText(
                    activity,
                    "fill empty field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Register()
                Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun Register() {
        binding.progressBar.visibility = View.VISIBLE
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, SignUpUrl,
            Response.Listener { response ->
                binding.progressBar.visibility = View.GONE
                try {
                    Log.d("look here praveen", response)
                    val jsonObject: JSONObject = JSONObject(response)
                    val success = jsonObject.getString("authtoken").toString()



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
                val Name = binding.UserName.text.toString().trim()
                val Email = binding.mailaddress.text.toString().trim()
                val Password = binding.password.text.toString().trim()
                val MobileNo =binding.MobileNo.text.trim()
                val Gender = binding.Gender.text.toString().trim()
                val Age = binding.Age.text.toString().trim()
                val experience = binding.Experience.text.toString().trim()
                val specialisation = binding.speacialisation.text.toString().trim()
                val regno = binding.regNo.text.toString().trim()

                val params1: HashMap<String, String> = HashMap()
//                params1["Content-Type"] = "application/json"
                params1["name"] = Name
                params1["password"] = Password
                params1["ph_number"] = MobileNo.toString()
                params1["email"] = Email
                params1["gender"] = Gender
                params1["age"] = Age.toString()
                params1["years_of_exp"] = experience
                params1["field_of_specialization"] = specialisation
                params1["reg_no"] = regno
                return params1
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
//        Toast.makeText(activity, "request code run", Toast.LENGTH_SHORT).show()
        requestQueue.add(stringRequest)
    }
}