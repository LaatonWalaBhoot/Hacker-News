package com.laatonwalabhoot.hackernews.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeUpdateUtils {

    private var date: Date = Calendar.getInstance().time
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
    private var timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    companion object {
        fun getInstance() = TimeUpdateUtils()
    }

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun getTimeDifference(time: String): String {
        /*
         * Aishwarya: Initializing string to be appended to latency
         * of the article to prevent any typos due to
         * singular or plural value
         */
        var int: Int = TimeUnit.MILLISECONDS.toDays(date.time - time.toLong() * 1000).toInt()
        return when (int) {
            0 -> {
                int = TimeUnit.MILLISECONDS.toHours(date.time - time.toLong() * 1000).toInt()
                when (int) {
                    0 -> {
                        int = TimeUnit.MILLISECONDS.toMinutes(date.time - time.toLong() * 1000).toInt()
                        when (int) {
                            1 -> int.toString().plus(" min ago")
                            else -> int.toString().plus(" mins ago")
                        }
                    }
                    1 -> {
                        int.toString().plus(" hour ago")
                    }
                    else -> int.toString().plus(" hours ago")
                }
            }
            1 -> {
                int.toString().plus(" day ago")
            }
            else -> int.toString().plus(" days ago")
        }
    }

    fun getFormattedTimeFromTime(time: String): String {
        return timeFormat.format(Date(time.toLong() * 1000))
    }

    fun getDateFromTime(time: String): String {
        return dateFormat.format(Date(time.toLong() * 1000))
    }
}