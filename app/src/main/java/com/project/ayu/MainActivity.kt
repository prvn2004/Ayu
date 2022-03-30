package com.project.ayu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.ayu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.patientButton.setOnClickListener {
            val intent = Intent(this, PatientLogin::class.java)
            startActivity(intent)
            finish()

//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
        }

        binding.DoctorButton.setOnClickListener {
            val intent = Intent(this, doctorLogin::class.java)
            startActivity(intent)
            finish()
        }

    }
}