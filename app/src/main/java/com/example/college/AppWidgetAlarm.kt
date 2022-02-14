package com.example.college

import android.app.AlarmManager

import android.app.PendingIntent
import android.content.Context

import android.content.Intent
import java.util.*


class AppWidgetAlarm(context: Context) {
    private val ALARM_ID = 0
    private val INTERVAL_MILLIS = 10000
    private var mContext: Context = context

    fun startAlarm() {

        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MILLISECOND, INTERVAL_MILLIS)
        val alarmIntent = Intent(mContext, InfoDisplay::class.java)
        alarmIntent.action = InfoDisplay.ACTION_AUTO_UPDATE
        val pendingIntent = PendingIntent.getBroadcast(
            mContext,
            ALARM_ID,
            alarmIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // RTC does not wake the device up
        alarmManager.setRepeating(
            AlarmManager.RTC,
            calendar.getTimeInMillis(),
            INTERVAL_MILLIS.toLong(),
            pendingIntent
        )
    }


    fun stopAlarm() {
        val alarmIntent = Intent(InfoDisplay.ACTION_AUTO_UPDATE)
        val pendingIntent = PendingIntent.getBroadcast(
            mContext,
            ALARM_ID,
            alarmIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}