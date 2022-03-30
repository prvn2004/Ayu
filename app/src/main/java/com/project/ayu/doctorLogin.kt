package com.project.ayu

import android.nfc.Tag
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.project.ayu.databinding.ActivityMainBinding
import com.project.ayu.databinding.DoctorLoginActivityBinding
import com.project.ayu.ui.main.DoctorLoginFragment
import com.project.ayu.ui.main.DoctorSignUp
import java.lang.reflect.Array.newInstance

class doctorLogin : AppCompatActivity() {
    lateinit var binding: DoctorLoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorLoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment(DoctorLoginFragment())

    }

    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragLayout, fragment)
        fram.commit()
    }

    fun SignUpClickDoctor(view: View){
        showFragment(DoctorSignUp())
    }
    fun SignInClickDoctor(view: View){
        showFragment(DoctorLoginFragment())
    }


}