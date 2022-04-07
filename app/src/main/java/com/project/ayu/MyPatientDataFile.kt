package com.project.ayu;

import android.app.DatePickerDialog
import android.media.Image
import android.telephony.ims.ImsRegistrationAttributes
import androidx.constraintlayout.widget.ReactiveGuide
import java.sql.Date
import java.sql.Time
import java.util.jar.Attributes

data class MyPatientDataFile(
    val PatientNumber: String,
    val DoctorNumber: String,
    val DoctorTime: String,
    val DoctorDate: String,
    val DoctorId: String

) {

    var NumberPatient = PatientNumber
    var NumberDoctor = DoctorNumber
    var TimeDoctor = DoctorTime
    var DateDoctor = DoctorDate
//    var TimeDoctor = DoctorTime
var IdDoctor = DoctorId


    init {
        NumberPatient = PatientNumber
        NumberDoctor = DoctorNumber
        TimeDoctor = DoctorTime
        DateDoctor = DoctorDate

    }

    fun getPatient(): String{
        return NumberPatient
    }

    fun getDoctor(): String {
        return NumberDoctor
    }

    fun getTime(): String {
        return TimeDoctor
    }

    fun getDate(): String {
        return DateDoctor
    }
    fun getId(): String {
        return IdDoctor
    }


}