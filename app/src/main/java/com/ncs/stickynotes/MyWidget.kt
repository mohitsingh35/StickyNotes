package com.ncs.stickynotes

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.LinearLayout
import android.widget.RemoteViews
import com.ncs.stickynotes.R

class MyWidget : AppWidgetProvider() {
    private lateinit var linearLayout: LinearLayout
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)

            // Set your desired content or configuration to the widget's layout here
            remoteViews.setTextViewText(R.id.widgetText, "Hello, Widget!")

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}
