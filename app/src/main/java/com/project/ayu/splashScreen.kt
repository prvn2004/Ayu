package com.project.ayu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        Handler(Looper.getMainLooper()).postDelayed({
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val DoctorLog = preferences.getString("DoctorLoginToken", "out")
            val PatientLog = preferences.getString("PatientLoginToken", "out")
            if (DoctorLog.equals("DoctorLoginToken")) {
                val intent = Intent(this, DoctorHomeActivity::class.java)
                startActivity(intent)
                finish()
            }else if (PatientLog.equals("PatientLoginToken")) {
                val intent3 = Intent(this, HomeActivity::class.java)
            startActivity(intent3)
            finish()

        }
            else {
                val Intent2 = Intent(this, MainActivity::class.java)
                startActivity(Intent2)
                finish()
                Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show()
            }
        }, 2000)
    }
}