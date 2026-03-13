package com.aariz.mediscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aariz.mediscan.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatCards(view)
        setupSettings(view)
    }

    private fun setupStatCards(view: View) {
        val statReports = view.findViewById<View>(R.id.profile_stat_reports)
        statReports.findViewById<TextView>(R.id.tv_stat_value).text = getString(R.string.reports_count)
        statReports.findViewById<TextView>(R.id.tv_stat_label).text = getString(R.string.stat_reports)

        val statAlerts = view.findViewById<View>(R.id.profile_stat_alerts)
        statAlerts.findViewById<TextView>(R.id.tv_stat_value).text = getString(R.string.flagged_count)
        statAlerts.findViewById<TextView>(R.id.tv_stat_label).text = "Alerts"

        val statAccuracy = view.findViewById<View>(R.id.profile_stat_accuracy)
        statAccuracy.findViewById<TextView>(R.id.tv_stat_value).text = getString(R.string.ai_accuracy)
        statAccuracy.findViewById<TextView>(R.id.tv_stat_label).text = getString(R.string.stat_accuracy)
    }

    private fun setupSettings(view: View) {
        view.findViewById<View>(R.id.setting_account).setOnClickListener {
            // Navigate to account settings
        }
        view.findViewById<View>(R.id.setting_notifications).setOnClickListener {
            // Navigate to notification settings
        }
        view.findViewById<View>(R.id.setting_privacy).setOnClickListener {
            // Navigate to privacy settings
        }
        view.findViewById<View>(R.id.setting_help).setOnClickListener {
            // Navigate to help
        }
        view.findViewById<View>(R.id.setting_logout).setOnClickListener {
            // Handle logout
        }
    }
}
