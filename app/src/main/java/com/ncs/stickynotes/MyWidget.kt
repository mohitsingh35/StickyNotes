package com.ncs.stickynotes

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class MyWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)

            // Set your desired content or configuration to the widget's layout here
            remoteViews.setTextViewText(R.id.widgetText, "Hello, Widget!")

            // Handle widget click to open the app
            remoteViews.setOnClickPendingIntent(
                R.id.widgetText,
                getPendingIntent(context)
            )

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}
