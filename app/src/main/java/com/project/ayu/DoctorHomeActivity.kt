package com.project.ayu

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.ayu.databinding.ActivityDoctorHomeBinding

class DoctorHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoctorHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val AppointmentFrag = Doctor_Appointments()
        val ProfileFrag = Doctor_profile()
setCurrentFragment(AppointmentFrag)

        binding.bottomNaviagtion.setOnItemSelectedListener {
            when(it.itemId){
                R.id.doctor_AppointmentsMenu -> setCurrentFragment(AppointmentFrag)
                R.id.doctor_ProfileMenu -> setCurrentFragment(ProfileFrag)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_container_doctor, fragment)
            commit()
        }
    }
}