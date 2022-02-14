package com.example.college

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import com.example.college.data.Days
import com.example.college.data.util


class InfoDisplay : AppWidgetProvider() {

        companion object {
            const val ACTION_AUTO_UPDATE = "AUTO_UPDATE"
        }

    override fun onReceive(context:Context, intent: Intent)
    {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)
        if(intent.action.equals(ACTION_AUTO_UPDATE))
        {
            onUpdate(context,appWidgetManager,appWidgetIds)
        }


    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        val intent=Intent(context,MainActivity::class.java)
        val pendingIntent=PendingIntent.getActivity(context,0,intent,0)
        val views=RemoteViews(context.packageName,R.layout.info_display)
        views.setOnClickPendingIntent(R.id.classNameWidget,pendingIntent)

        val utils =util(context.getSharedPreferences("classData", AppCompatActivity.MODE_PRIVATE))
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,utils)
        }
    }

    override fun onEnabled(context: Context) {
        // start alarm
        val appWidgetAlarm = AppWidgetAlarm(context.applicationContext)
        appWidgetAlarm.startAlarm()

    }

    override fun onDisabled(context: Context) {
        // stop alarm only if all widgets have been disabled
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)
        if (appWidgetIds.isEmpty()) {
            // stop alarm
            val appWidgetAlarm = AppWidgetAlarm(context.applicationContext)
            appWidgetAlarm.stopAlarm()
        }
    }

}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, utils:util) {
    val classData=utils.getLatestData()
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.info_display)
    views.setTextViewText(R.id.professorNameWidget,classData.professorName)
    views.setTextViewText(R.id.classNameWidget,classData.className)
    views.setTextViewText(R.id.classIdWidget,classData.classId)
    views.setTextViewText(R.id.dayWidget, Days.getDayForID(classData.day))
    views.setTextViewText(R.id.timeWidget, Days.getTime(classData.hours,classData.minutes))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}