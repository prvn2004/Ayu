package com.project.ayu

import android.R
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.ayu.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                com.project.ayu.R.id.logout -> {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                    val editor = preferences.edit()
                    editor.clear()
                    editor.apply()
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
            true
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val name = preferences.getString("name", "name")
        val Age = preferences.getString("Age", "Age")
        val gender = preferences.getString("gender", "gender")
        val email = preferences.getString("email", "email")
        val ph_number = preferences.getLong("Ph_number", 1234567890).toString()

        binding.imageView5.setImageResource(com.project.ayu.R.drawable.ic_baseline_tag_faces_24)
        binding.PatientName.text = name
        binding.patientAge.text = "Age: $Age"
        binding.PatientEmail.text = email
        binding.PatientGender.text = "Gender: $gender"
        binding.patientNumber.text = ph_number

        // Inflate the layout for this fragment
        return view
    }

//    fun logout(v: View?) {
//        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
//        val editor = preferences.edit()
//        editor.clear()
//        editor.apply()
//
//        val intent = Intent(activity, MainActivity::class.java)
//        startActivity(intent)
//        activity?.finish()
//    }


}
