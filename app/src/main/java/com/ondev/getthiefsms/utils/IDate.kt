package com.ondev.getthiefsms.utils

import java.text.SimpleDateFormat
import java.util.*

object IDate {

    const val APP_DATE_PATTER = "hh:MM yyyy/MM/dd"

    fun now(dateFormat: String?): String {
        val sdf = SimpleDateFormat(dateFormat)
        return sdf.format(Date())
    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time).toString()
    }

    fun nowInMillis(): Long {
        return System.currentTimeMillis()
    }

}