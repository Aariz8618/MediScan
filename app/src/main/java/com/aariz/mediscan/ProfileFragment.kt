package com.aariz.mediscan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * ProfileFragment
 *
 * Inflates fragment_profile.xml.
 * • Header: avatar initial + name + city + tags — all from UserPreferences
 * • Stats: reports / flagged / member — from UserPreferences
 * • Personal Info row → PersonalInfoActivity (back button returns here)
 * • Medical History / Medications / Allergies → "Coming Soon" toast
 * • Notifications toggle → saved in UserPreferences
 * • Privacy / Share / Help → "Coming Soon" toast
 * • Sign Out → AlertDialog → activity.finish()
 */
class ProfileFragment : Fragment() {

    private lateinit var prefs: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = UserPreferences(requireContext())
        setupClickListeners(view)
    }

    /** Called every time we return from PersonalInfoActivity. */
    override fun onResume() {
        super.onResume()
        prefs = UserPreferences(requireContext())
        bindHeader(requireView())
        bindStats(requireView())
        bindSwitch(requireView())
    }

    // ─── Header binding ──────────────────────────────────────────────────

    private fun bindHeader(v: View) {
        // Initial letter
        v.findViewById<TextView>(R.id.tv_initial).text = prefs.initial()

        // Name (no "Dr." prefix — just whatever user typed)
        val name = prefs.fullName.trim()
        v.findViewById<TextView>(R.id.tv_name).text = name.ifBlank { "Your Name" }

        // City subtitle
        val city = prefs.city.trim()
        v.findViewById<TextView>(R.id.tv_role).apply {
            text = city
            visibility = if (city.isNotBlank()) View.VISIBLE else View.GONE
        }

        // Tag 1 — city (or default)
        v.findViewById<TextView>(R.id.tv_tag1).text =
            if (city.isNotBlank()) "🏥 $city" else "🏥 MedScan"

        // Tag 2 — sex + age
        val tag2 = buildString {
            if (prefs.sex.isNotBlank()) append(prefs.sex.first().uppercaseChar())
            if (prefs.age.isNotBlank()) {
                if (isNotEmpty()) append(" · ")
                append(prefs.age).append("yrs")
            }
        }
        v.findViewById<TextView>(R.id.tv_tag2).apply {
            text = tag2
            visibility = if (tag2.isNotBlank()) View.VISIBLE else View.GONE
        }

        // Tag 3 — blood group
        val bg = prefs.bloodGroup.trim()
        v.findViewById<TextView>(R.id.tv_tag3).apply {
            text = bg
            visibility = if (bg.isNotBlank()) View.VISIBLE else View.GONE
        }
    }

    // ─── Stats binding ───────────────────────────────────────────────────

    private fun bindStats(v: View) {
        v.findViewById<TextView>(R.id.tv_stat_reports).text  = prefs.totalReports.toString()
        v.findViewById<TextView>(R.id.tv_stat_flagged).text  = prefs.flaggedReports.toString()
        v.findViewById<TextView>(R.id.tv_stat_member).text   = prefs.memberLabel()

        // Live value badges next to rows
        val cond = prefs.conditions.size
        val med  = prefs.medications.size
        val alg  = prefs.allergies.size

        v.findViewById<TextView>(R.id.tv_medical_count).text =
            if (cond > 0) "$cond entries" else "None"
        v.findViewById<TextView>(R.id.tv_med_count).text =
            if (med > 0) "$med active" else "None"
        v.findViewById<TextView>(R.id.tv_allergy_count).text =
            if (alg > 0) "$alg" else "None"
    }

    // ─── Notifications switch ────────────────────────────────────────────

    private fun bindSwitch(v: View) {
        val sw = v.findViewById<SwitchMaterial>(R.id.switch_notifications)
        sw.isChecked = prefs.notificationsEnabled
        sw.setOnCheckedChangeListener { _, checked ->
            prefs.notificationsEnabled = checked
        }
    }

    // ─── Click listeners ─────────────────────────────────────────────────

    private fun setupClickListeners(v: View) {

        // Personal Info → opens PersonalInfoActivity
        v.findViewById<LinearLayout>(R.id.row_personal_info).setOnClickListener {
            startActivity(Intent(requireContext(), PersonalInfoActivity::class.java))
        }

        // Medical History — coming soon
        v.findViewById<LinearLayout>(R.id.row_medical_history).setOnClickListener {
            showComingSoon()
        }

        // Medications — coming soon
        v.findViewById<LinearLayout>(R.id.row_medications).setOnClickListener {
            showComingSoon()
        }

        // Allergies — coming soon
        v.findViewById<LinearLayout>(R.id.row_allergies).setOnClickListener {
            showComingSoon()
        }

        // Privacy — coming soon
        v.findViewById<LinearLayout>(R.id.row_privacy).setOnClickListener {
            showComingSoon()
        }

        // Share — coming soon
        v.findViewById<LinearLayout>(R.id.row_share).setOnClickListener {
            showComingSoon()
        }

        // Help — coming soon
        v.findViewById<LinearLayout>(R.id.row_help).setOnClickListener {
            showComingSoon()
        }

        // Sign Out → confirmation dialog
        v.findViewById<LinearLayout>(R.id.row_sign_out).setOnClickListener {
            showSignOutDialog()
        }
    }

    // ─── Dialogs ─────────────────────────────────────────────────────────

    private fun showSignOutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Sign Out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Sign Out") { _, _ ->
                requireActivity().finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showComingSoon() {
        android.widget.Toast.makeText(requireContext(), "Coming soon!", android.widget.Toast.LENGTH_SHORT).show()
    }
}