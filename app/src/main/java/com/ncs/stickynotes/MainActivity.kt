package com.ncs.stickynotes

import android.appwidget.AppWidgetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent?.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val fragment = MainFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, fragment)
                .commit()
        }
    }
}
