package com.project.ayu

import android.R
import android.R.*
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.project.ayu.databinding.FragmentDoctorProfileBinding


class Doctor_profile : Fragment(), Toolbar.OnMenuItemClickListener {
    lateinit var binding: FragmentDoctorProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val name = preferences.getString("name", "name")
        val Age = preferences.getString("Age", "Age")
        val gender = preferences.getString("gender", "gender")
        val email = preferences.getString("email", "email")
        val ph_number = preferences.getLong("Ph_number", 1234567890).toString()


        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                com.project.ayu.R.id.logout_doctor -> {
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

        binding.imageView5.setImageResource(com.project.ayu.R.drawable.doctoravatarmale)
        binding.DoctorName.text = name
        binding.DoctorAge.text = "Age: $Age"
        binding.DoctorEmail.text = email
        binding.DoctorGender.text = "Gender: $gender"
        binding.DoctorNumber.text = ph_number

        // Inflate the layout for this fragment
        return view

    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        when (menuItem!!.itemId) {
            com.project.ayu.R.id.logout_doctor -> {
            if (menuItem.itemId !== com.project.ayu.R.id.logout_doctor) Toast.makeText(activity, "clickied", Toast.LENGTH_SHORT).show() else Toast.makeText(activity, "number not clicked", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }


}