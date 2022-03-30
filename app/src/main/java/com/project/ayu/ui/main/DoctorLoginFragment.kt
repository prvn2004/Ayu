package com.project.ayu.ui.main


import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.ayu.DoctorHomeActivity
import com.project.ayu.HomeActivity
import com.project.ayu.R
import com.project.ayu.databinding.MainFragmentBinding
import org.json.JSONObject


class DoctorLoginFragment : Fragment() {
    private var SignInUrl = "http://ayubackend.herokuapp.com/api/auth/login"
    lateinit var binding: MainFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.SignIn.setOnClickListener {
            val Email = binding.mailaddress.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = preferences.edit()
            editor.putString("DoctorLoginToken", "DoctorLoginToken")
            editor.apply()


            if (password.isEmpty() || Email.isEmpty()) {
                Toast.makeText(
                    activity,
                    "please enter valid data",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Login()
                Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show()
            }

        }

        return view


    }

    private fun Login() {
        binding.progressBar2.visibility = View.VISIBLE
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, SignInUrl,
            Response.Listener { response ->
                binding.progressBar2.visibility = View.GONE

                try {
                    Log.d("look here praveen", response)
                    val jsonObject1: JSONObject = JSONObject(response)
                    val newObject: JSONObject = jsonObject1.getJSONObject("user")
                    val name = newObject.getString("name")
                    val success = jsonObject1.getString("authtoken")
                    val age = newObject.getString("age")
                    val gender = newObject.getString("gender")
                    val email = newObject.getString("email")
                    val ph_number = newObject.getLong("ph_number")
                    Log.d("hey here is succes", success)
                    val email1 = binding.mailaddress.text.toString().trim()
                    if (email.equals(email1)) {
                        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                        val editor = preferences.edit()
                        editor.putString("this", "LogIn")
                        editor.putString("authtoken", success)
                        editor.putString("name", name)
                        editor.putString("Age", age)
                        editor.putString("gender", gender)
                        editor.putString("email", email)
                        editor.putLong("Ph_number", ph_number)
                        editor.apply()
                    }else{
                        Toast.makeText(activity, "some error is here", Toast.LENGTH_SHORT).show()
                    }

                    val login = Intent(activity, DoctorHomeActivity::class.java)
                    startActivity(login)
                    activity?.finish()

                } catch (e: Exception) {
                    binding.progressBar2.visibility = View.GONE
                    e.printStackTrace()
                    Toast.makeText(
                        activity,
                        "$e", Toast.LENGTH_LONG
                    ).show()
                }
            }, Response.ErrorListener { error ->
                binding.progressBar2.visibility = View.GONE

                Toast.makeText(
                    activity,
                    "$error", Toast.LENGTH_LONG
                ).show()
            }) {

            override fun getParams(): Map<String, String> {
                val Email = binding.mailaddress.text.toString().trim()
                val password = binding.password.text.toString().trim()

                val params1: HashMap<String, String> = HashMap()
//                params1["Content-Type"] = "application/json"
                params1["email"] = Email
                params1["password"] = password

                return params1
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
//
//        .makeText(activity, "request code run", Toast.LENGTH_SHORT).show()
        requestQueue.add(stringRequest)
    }
    }


