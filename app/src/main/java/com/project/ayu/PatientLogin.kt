package com.project.ayu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.project.ayu.databinding.ActivityPatientLoginBinding
import com.project.ayu.databinding.MainFragmentBinding
import com.project.ayu.ui.main.PatientLoginFragment
import com.project.ayu.ui.main.PatientSignUp

class PatientLogin : AppCompatActivity() {
    private lateinit var binding: ActivityPatientLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        showFragment(PatientLoginFragment())


    }

    fun showFragment(fragment: Fragment) {
        val fram1 = supportFragmentManager.beginTransaction()
        fram1.replace(R.id.fragLayout2, fragment)
        fram1.commit()
    }

//    fun OnClick(view: View) {
//        val intent = Intent(this, HomeActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    fun SignInClick(view: View){
        showFragment(PatientLoginFragment())
    }

    fun SignUpClick(view: View) {
        showFragment(PatientSignUp())
    }


}