package com.aariz.mediscan

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aariz.mediscan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHome:     LinearLayout
    private lateinit var navReports:  LinearLayout
    private lateinit var navAnalysis: LinearLayout
    private lateinit var navProfile:  LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = android.graphics.Color.parseColor("#1B7A5A")

        navHome     = binding.navHome
        navReports  = binding.navReports
        navAnalysis = binding.navAnalysis
        navProfile  = binding.navProfile

        // ── Edge-to-edge + inset handling ────────────────────────────────
        // Automatically adapts for gesture navigation (swipe pill) AND
        // 3-button navigation — no hardcoded dp values needed.
        WindowInsetHelper.apply(
            activity          = this,
            floatingNavCard   = binding.bnav,              // CardView in CoordinatorLayout
            fragmentContainer = binding.fragmentContainer,
            baseNavMarginDp   = 14                         // matches layout_marginBottom in XML
        )
        // ─────────────────────────────────────────────────────────────────

        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), "home", addToBack = false)
            setActiveNav(navHome)
        }

        navHome.setOnClickListener {
            supportFragmentManager.popBackStack(null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            loadFragment(HomeFragment(), "home", addToBack = false)
            setActiveNav(navHome)
        }

        navReports.setOnClickListener {
            loadFragment(ReportsFragment(), "reports", addToBack = true)
            setActiveNav(navReports)
        }

        navAnalysis.setOnClickListener {
            loadFragment(AnalysisFragment(), "analysis", addToBack = true)
            setActiveNav(navAnalysis)
        }

        navProfile.setOnClickListener {
            loadFragment(ProfileFragment(), "profile", addToBack = true)
            setActiveNav(navProfile)
        }

        // ── Back press — replaces deprecated onBackPressed() ────────────
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    // Sync nav highlight back to Home when stack becomes empty
                    if (supportFragmentManager.backStackEntryCount == 1) {
                        setActiveNav(navHome)
                    }
                } else {
                    // No back stack — let the system handle it (exit app)
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    // ── Called by child fragments to navigate within the container ──────
    fun navigateTo(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(binding.fragmentContainer.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    private fun loadFragment(fragment: Fragment, tag: String, addToBack: Boolean) {
        val tx = supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(binding.fragmentContainer.id, fragment, tag)

        if (addToBack) tx.addToBackStack(tag)
        tx.commit()
    }

    fun setActiveNav(activeNav: LinearLayout) {
        listOf(navHome, navReports, navAnalysis, navProfile).forEach { nav ->
            val icon  = nav.getChildAt(0)
            val label = nav.getChildAt(1)
            val dot   = nav.getChildAt(2)
            val isActive = nav == activeNav
            val tint = if (isActive) "#1B7A5A" else "#A0B5B1"

            if (icon  is android.widget.ImageView)
                icon.imageTintList = android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.parseColor(tint))
            if (label is android.widget.TextView)
                label.setTextColor(android.graphics.Color.parseColor(tint))
            if (dot is View)
                dot.visibility = if (isActive) View.VISIBLE else View.GONE
        }
    }
}