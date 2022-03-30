package com.project.ayu

import android.media.Image
import android.telephony.ims.ImsRegistrationAttributes
import androidx.constraintlayout.widget.ReactiveGuide
import java.util.jar.Attributes

data class DoctorDataFile(
    val DoctorName: String? = "",
    val DoctorNumber: String,
    val DoctorImage: Int,
    val DoctorExpertise: String,
    val DoctorExperience: String,
    val DoctorRegNo: String
//    val DoctorTime:String
) {
    var NameDoctor = DoctorName
    var NumberDoctor = DoctorNumber
    var ImageDoctor = DoctorImage
    var ExpertiseDoctor = DoctorExpertise
    var ExperienceDoctor = DoctorExperience
    var RegNoDoctor = DoctorRegNo
//    var TimeDoctor = DoctorTime

    init {
        NameDoctor = DoctorName.toString()
        NumberDoctor = DoctorNumber
        ImageDoctor = DoctorImage
        ExpertiseDoctor = DoctorExpertise
        ExperienceDoctor = DoctorExperience
        RegNoDoctor = DoctorRegNo
//        TimeDoctor = DoctorTime

    }

    fun getName(): String? {
        return NameDoctor
    }

    fun getNumber(): String {
        return NumberDoctor
    }

    fun getImage(): Int {
        return ImageDoctor
    }

    fun getExpertise(): String {
        return ExpertiseDoctor
    }

    fun getExperience(): String {
        return ExperienceDoctor
    }

    //    fun getTime():String{
//        return TimeDoctor
//    }
    fun getRegNo(): String {
        return RegNoDoctor
    }


}
