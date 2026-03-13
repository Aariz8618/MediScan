package com.aariz.mediscan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aariz.mediscan.databinding.FragmentHomeBinding
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserInfo()
        loadStats()
        loadRecentReports()
        setupClickListeners()
    }

    // ─────────────────────────────────────────────────────────
    //  1. User info — name comes from SharedPreferences
    //     (saved during sign-up / login)
    // ─────────────────────────────────────────────────────────
    private fun loadUserInfo() {
        val prefs = requireContext()
            .getSharedPreferences("mediscan_user", Context.MODE_PRIVATE)

        // "user_display_name" is saved at login/signup time
        val fullName = prefs.getString("user_display_name", "") ?: ""

        if (fullName.isNotBlank()) {
            binding.tvUserName.text = fullName
            // Avatar initial = first letter of name
            binding.tvAvatarInitial.text = fullName.first().uppercaseChar().toString()
        } else {
            // Fallback if prefs not yet populated
            binding.tvUserName.text = "Welcome!"
            binding.tvAvatarInitial.text = "?"
        }

        // Time-based greeting
        binding.tvGreeting.text = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11  -> "Good morning 👋"
            in 12..16 -> "Good afternoon 👋"
            in 17..20 -> "Good evening 👋"
            else      -> "Hello 👋"
        }
    }

    // ─────────────────────────────────────────────────────────
    //  2. Stats — read from SharedPreferences
    //     Updated every time user uploads a report
    // ─────────────────────────────────────────────────────────
    private fun loadStats() {
        val prefs = requireContext()
            .getSharedPreferences("mediscan_stats", Context.MODE_PRIVATE)

        val totalReports  = prefs.getInt("total_reports", 0)
        val weekReports   = prefs.getInt("week_reports", 0)
        val accuracyPct   = prefs.getInt("accuracy_pct", -1)   // -1 = not yet set

        binding.tvStatReports.text  = totalReports.toString()
        binding.tvStatWeek.text     = weekReports.toString()
        binding.tvStatAccuracy.text = if (accuracyPct >= 0) "$accuracyPct%" else "–"

        // AI insight subtitle: update once there are actual reports
        if (totalReports > 0) {
            binding.tvInsightSub.text = "Your latest report needs attention"
        } else {
            binding.tvInsightSub.text = "Upload a report to get AI insights"
        }
    }

    // ─────────────────────────────────────────────────────────
    //  3. Recent reports — show empty state OR list
    //     Toggle visibility based on whether user has uploads
    // ─────────────────────────────────────────────────────────
    private fun loadRecentReports() {
        val prefs = requireContext()
            .getSharedPreferences("mediscan_stats", Context.MODE_PRIVATE)
        val hasReports = prefs.getInt("total_reports", 0) > 0

        binding.emptyReportsState.visibility  = if (hasReports) View.GONE  else View.VISIBLE
        binding.reportsListContainer.visibility = if (hasReports) View.VISIBLE else View.GONE
    }

    // ─────────────────────────────────────────────────────────
    //  4. Click listeners
    // ─────────────────────────────────────────────────────────
    private fun setupClickListeners() {

        // Upload card → go to Upload screen
        binding.cardUpload.setOnClickListener {
            (activity as? MainActivity)?.navigateTo(UploadFragment(), "upload")
        }

        // AI Insight strip → open latest report detail
        binding.cardAiInsight.setOnClickListener {
            Toast.makeText(requireContext(), "Opening AI insight…", Toast.LENGTH_SHORT).show()
        }

        // Scan type chips
        binding.chipLab.setOnClickListener     { navigateToScan("lab") }
        binding.chipEcg.setOnClickListener     { navigateToScan("ecg") }
        binding.chipMri.setOnClickListener     { navigateToScan("mri") }
        binding.chipXray.setOnClickListener    { navigateToScan("xray") }
        binding.chipRetinal.setOnClickListener { navigateToScan("retinal") }
        binding.chipRx.setOnClickListener      { navigateToScan("prescription") }

        // Recent report cards
        binding.reportCbc.setOnClickListener   { Toast.makeText(requireContext(), "Opening CBC report…", Toast.LENGTH_SHORT).show() }
        binding.reportEcg.setOnClickListener   { Toast.makeText(requireContext(), "Opening ECG report…", Toast.LENGTH_SHORT).show() }
        binding.reportMri.setOnClickListener   { Toast.makeText(requireContext(), "Opening MRI report…", Toast.LENGTH_SHORT).show() }
        binding.reportXray.setOnClickListener  { Toast.makeText(requireContext(), "Opening X-Ray report…", Toast.LENGTH_SHORT).show() }
    }

    private fun navigateToScan(type: String) {
        // TODO: launch detail screen for this scan type
        Toast.makeText(requireContext(), "Opening $type analysis…", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ─────────────────────────────────────────────────────────
    //  COMPANION — call this from Login/Signup to save name
    // ─────────────────────────────────────────────────────────
    companion object {
        /**
         * Call this right after a successful login or sign-up:
         *   HomeFragment.saveUserProfile(context, "Dr. Aarav Shah")
         */
        fun saveUserProfile(context: Context, displayName: String) {
            context.getSharedPreferences("mediscan_user", Context.MODE_PRIVATE)
                .edit()
                .putString("user_display_name", displayName)
                .apply()
        }

        /**
         * Call this after every successful report upload to increment counters:
         *   HomeFragment.incrementReportStats(context, accuracyPercent = 98)
         */
        fun incrementReportStats(context: Context, accuracyPercent: Int = 98) {
            val prefs = context.getSharedPreferences("mediscan_stats", Context.MODE_PRIVATE)
            val total = prefs.getInt("total_reports", 0) + 1
            val week  = prefs.getInt("week_reports",  0) + 1
            prefs.edit()
                .putInt("total_reports",  total)
                .putInt("week_reports",   week)
                .putInt("accuracy_pct",   accuracyPercent)
                .apply()
        }
    }
}