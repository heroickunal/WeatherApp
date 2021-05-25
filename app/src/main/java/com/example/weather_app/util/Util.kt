package com.example.weather_app.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*


object Util {

    fun parseDateToDay(date: String?): String {
        //Output -> Sun
        val outputPattern = "E"

        return if (date.isNullOrEmpty()) {
            "NA"
        } else {
            return parseDates(date, outputPattern)
        }
    }


    fun parseDates(dateString: String?, outputPattern: String): String {
        val inputPattern = "yyyy-MM-dd"
        val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
        val outputFormat = SimpleDateFormat(outputPattern, Locale.ENGLISH)
        return if (dateString.isNullOrEmpty()) {
            "NA"
        } else {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        }
    }

    fun getGradientColor(context: Context,_topColor:Int,_bottomColor:Int):Drawable{
        val topColor = ContextCompat.getColor(context, _topColor)
        val bottomColor = ContextCompat.getColor(context, _bottomColor)
        val colors = intArrayOf(
            bottomColor,
            topColor
        )
        val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, colors)
        gd.cornerRadius = 0f
        return gd
    }
}
