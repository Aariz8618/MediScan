package com.aariz.mediscan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import com.aariz.mediscan.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Full bleed dark
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor     = android.graphics.Color.parseColor("#1A1E24")
        window.navigationBarColor = android.graphics.Color.parseColor("#1A1E24")

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Wait for layout pass before animating — avoids black screen
        binding.root.doOnLayout {
            runAnimations()
        }
    }

    private fun runAnimations() {

        // ── 1. Orb glow blooms ────────────────────────────────────────────
        binding.orbGlow.animate()
            .alpha(1f)
            .setStartDelay(80)
            .setDuration(700)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // ── 2. Icon orb springs in ────────────────────────────────────────
        binding.iconOrb.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(200)
            .setDuration(480)
            .setInterpolator(OvershootInterpolator(1.5f))
            .start()

        // ── 3. App name slides up ─────────────────────────────────────────
        binding.tvAppName.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(480)
            .setDuration(380)
            .setInterpolator(DecelerateInterpolator(2.2f))
            .start()

        // ── 4. Tagline fades in ───────────────────────────────────────────
        binding.tvTagline.animate()
            .alpha(1f)
            .setStartDelay(660)
            .setDuration(340)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // ── 5. Cyan line — set pivot AFTER layout so scaleX works correctly
        binding.cyanLine.doOnLayout { line ->
            line.pivotX = line.width / 2f
            line.scaleX = 0f
            line.animate()
                .alpha(1f)
                .scaleX(1f)
                .setStartDelay(900)
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }

        // ── 6. Version fades in ───────────────────────────────────────────
        binding.tvVersion.animate()
            .alpha(1f)
            .setStartDelay(1050)
            .setDuration(300)
            .start()

        // ── 7. Navigate to MainActivity ───────────────────────────────────
        binding.root.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2400)
    }
}