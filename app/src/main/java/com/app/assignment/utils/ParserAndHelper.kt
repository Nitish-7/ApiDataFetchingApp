package com.app.assignment.utils

import android.icu.text.SimpleDateFormat
import java.util.*

class ParserAndHelper {
    companion object {
        fun getFormatedTime(): String {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return sdf.format(Date())
        }
    }
}