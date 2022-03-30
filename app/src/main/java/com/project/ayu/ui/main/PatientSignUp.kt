package com.project.ayu.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.PatientLogin
import com.project.ayu.databinding.FragmentPatientSignUpBinding
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap


class PatientSignUp : Fragment() {
    private var SignUpUrl = "http://ayubackend.herokuapp.com/api/auth/usersignup"
    private lateinit var binding: FragmentPatientSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPatientSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signUpPatient.setOnClickListener {
            val Name = binding.PatientName.text.toString().trim()
            val Email = binding.mailaddress.text.toString().trim()
            val Password = binding.password.text.toString().trim()
            val MobileNo = binding.password.text.trim()
            val Gender = binding.Gender.text.toString().trim()
            val Age = binding.Age.text.toString().trim()
            if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty() || MobileNo.isEmpty() || Gender.isEmpty() || Age.isEmpty()) {
                Toast.makeText(
                    activity,
                    "please enter valid data",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Register()
                Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show()
            }
        }

        // Inflate the layout for this fragment
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
                    val success = jsonObject.getString("authtoken")
                    if (success.length>0){
                        Toast.makeText(activity, "your account created. click on login button", Toast.LENGTH_LONG).show()
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
                val Name = binding.PatientName.text.toString().trim()
                val Email = binding.mailaddress.text.toString().trim()
                val Password = binding.password.text.toString().trim()
                val MobileNo = binding.MobileNo.text.trim()
                val Gender = binding.Gender.text.toString().trim()
                val Age = binding.Age.text.trim()


                val params1: HashMap<String, String> = HashMap()
//                params1["Content-Type"] = "application/json"
                params1["name"] = Name
                params1["password"] = Password
                params1["ph_number"] = MobileNo.toString()
                params1["email"] = Email
                params1["gender"] = Gender
                params1["age"] = Age.toString()
                return params1
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
//        Toast.makeText(activity, "request code run", Toast.LENGTH_SHORT).show()
        requestQueue.add(stringRequest)

    }

}


