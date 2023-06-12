package com.ncs.stickynotes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    private val splashDelay: Long = 3000
    lateinit var textView: TextView
    lateinit var textView2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)
        textView=findViewById(R.id.name)
        textView2=findViewById(R.id.byname)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        textView.startAnimation(fadeInAnimation)
        textView2.startAnimation(fadeInAnimation)
    }
}
