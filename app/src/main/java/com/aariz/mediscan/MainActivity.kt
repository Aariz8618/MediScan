package com.aariz.mediscan

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    // Tab identifiers
    enum class Tab { HOME, REPORTS, ANALYZE, PROFILE }

    private var currentTab = Tab.HOME

    // Nav item data
    private data class NavItem(
        val container: LinearLayout,
        val icon: ImageView,
        val label: TextView,
        val pip: View,
        val tab: Tab
    )

    private lateinit var navItems: List<NavItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navItems = listOf(
            NavItem(
                findViewById(R.id.navHome),
                findViewById(R.id.navHomeIcon),
                findViewById(R.id.navHomeLabel),
                findViewById(R.id.navHomePip),
                Tab.HOME
            ),
            NavItem(
                findViewById(R.id.navReports),
                findViewById(R.id.navReportsIcon),
                findViewById(R.id.navReportsLabel),
                findViewById(R.id.navReportsPip),
                Tab.REPORTS
            ),
            NavItem(
                findViewById(R.id.navAnalyze),
                findViewById(R.id.navAnalyzeIcon),
                findViewById(R.id.navAnalyzeLabel),
                findViewById(R.id.navAnalyzePip),
                Tab.ANALYZE
            ),
            NavItem(
                findViewById(R.id.navProfile),
                findViewById(R.id.navProfileIcon),
                findViewById(R.id.navProfileLabel),
                findViewById(R.id.navProfilePip),
                Tab.PROFILE
            )
        )

        navItems.forEach { item ->
            item.container.setOnClickListener { selectTab(item.tab) }
        }

        // Listen to back-stack changes to keep nav in sync
        supportFragmentManager.addOnBackStackChangedListener {
            syncNavFromCurrentFragment()
        }

        if (savedInstanceState == null) {
            selectTab(Tab.HOME)
        }
    }

    /** Switch the root-level tab, clearing the back stack. */
    fun selectTab(tab: Tab) {
        currentTab = tab
        val fragment: Fragment = when (tab) {
            Tab.HOME    -> HomeFragment()
            Tab.REPORTS -> ReportsFragment()
            Tab.ANALYZE -> AnalysisEmptyFragment()
            Tab.PROFILE -> ProfileFragment()
        }
        // Pop everything, replace with new root
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        updateNavUI(tab)
    }

    private fun syncNavFromCurrentFragment() {
        val frag = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val tab = when (frag) {
            is HomeFragment          -> Tab.HOME
            is ReportsFragment       -> Tab.REPORTS
            is AnalysisEmptyFragment -> Tab.ANALYZE
            is ProfileFragment       -> Tab.PROFILE
            // Detail screens belong under Reports tab
            is LabFragment, is EcgFragment, is MriFragment,
            is XrayFragment, is CtFragment, is RetinalFragment,
            is PrescriptionFragment  -> Tab.REPORTS
            is UploadFragment, is ProcessingFragment -> Tab.REPORTS
            is ShareFragment         -> Tab.PROFILE
            else                     -> currentTab
        }
        updateNavUI(tab)
    }

    private fun updateNavUI(activeTab: Tab) {
        val activeColor = ContextCompat.getColor(this, R.color.hdr)
        val inactiveColor = ContextCompat.getColor(this, R.color.muted)

        navItems.forEach { item ->
            val isActive = item.tab == activeTab
            item.label.setTextColor(if (isActive) activeColor else inactiveColor)
            item.label.setTypeface(null, if (isActive) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
            item.pip.visibility = if (isActive) View.VISIBLE else View.INVISIBLE

            // Tint icon
            val tint = if (isActive) activeColor else inactiveColor
            item.icon.colorFilter = PorterDuffColorFilter(tint, PorterDuff.Mode.SRC_IN)

            // Background highlight on active icon area
            if (isActive) {
                item.icon.setBackgroundResource(R.drawable.bg_nav_icon_active)
            } else {
                item.icon.setBackgroundResource(0)
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (currentTab != Tab.HOME) {
            selectTab(Tab.HOME)
        } else {
            super.onBackPressed()
        }
    }
}
