package com.project.ayu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.project.ayu.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val AppointmentFrag = AppointmentFragment()
        val ProfileFrag = ProfileFragment()
//        val profile_doc_pat = Patient_viewing_doctor_profile()

        setCurrentFragment(AppointmentFrag)
        binding.bottomNaviagtion.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.DoctorMenu -> setCurrentFragment(AppointmentFrag)
                R.id.ProfileMenu -> setCurrentFragment(ProfileFrag)
                R.id.AppointmentsMenu -> setCurrentFragment(Past_Appointments())
            }
            true
        }

    }
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_container, fragment)
            commit()
        }
    }
}