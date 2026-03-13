package com.aariz.mediscan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aariz.mediscan.fragments.HomeFragment
import com.aariz.mediscan.fragments.ProfileFragment
import com.aariz.mediscan.fragments.ReportsFragment
import com.aariz.mediscan.fragments.UploadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_nav)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home     -> HomeFragment()
                R.id.nav_reports  -> ReportsFragment()
                R.id.nav_analyze  -> UploadFragment()
                R.id.nav_profile  -> ProfileFragment()
                else              -> HomeFragment()
            }
            loadFragment(fragment)
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
