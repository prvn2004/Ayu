package com.project.ayu

import android.media.Image
import android.telephony.ims.ImsRegistrationAttributes
import java.sql.Time
import java.util.jar.Attributes

data class PastAppointmentsDataFile(
    val DoctorTime: String,
    val DoctorNumber: String,
    val DoctorImage: Int,
    val DoctorDate: String,
) {
    var TimeDoctor = DoctorTime
    var NumberDoctor = DoctorNumber
    var ImageDoctor = DoctorImage
    var DateDoctor = DoctorDate

    init {
        TimeDoctor = DoctorTime.toString()
        NumberDoctor = DoctorNumber
        ImageDoctor = DoctorImage
        DateDoctor = DoctorDate

    }

    fun getTime(): String? {
        return TimeDoctor
    }

    fun getNumber(): String {
        return NumberDoctor
    }

    fun getImage(): Int {
        return ImageDoctor
    }
    fun getDate():String{
        return  DateDoctor
    }


}
