package com.aariz.mediscan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Find views
        val iconOrb = findViewById<View>(R.id.iconOrb)
        val orbGlow = findViewById<View>(R.id.orbGlow)
        val tvAppName = findViewById<TextView>(R.id.tvAppName)
        val tvTagline = findViewById<TextView>(R.id.tvTagline)
        val cyanLine = findViewById<View>(R.id.cyanLine)
        val tvVersion = findViewById<TextView>(R.id.tvVersion)

        // Simple enter animations
        iconOrb.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(800).setStartDelay(200).start()
        orbGlow.animate().alpha(0.15f).setDuration(1200).setStartDelay(400).start()
        
        tvAppName.animate().alpha(1f).translationY(0f).setDuration(700).setStartDelay(600)
            .setInterpolator(AccelerateDecelerateInterpolator()).start()
        
        tvTagline.animate().alpha(1f).setDuration(600).setStartDelay(900).start()
        cyanLine.animate().alpha(1f).setDuration(600).setStartDelay(1100).start()
        tvVersion.animate().alpha(1f).setDuration(600).setStartDelay(1200).start()

        // Transition to MainActivity after 2.5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 2500)
    }
}
