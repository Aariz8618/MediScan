package com.aariz.mediscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aariz.mediscan.R
import com.aariz.mediscan.model.Report

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatCards(view)
        setupQuickChips(view)
        setupRecentReports(view)
    }

    private fun setupStatCards(view: View) {
        val statReports = view.findViewById<View>(R.id.stat_card_reports)
        statReports.findViewById<TextView>(R.id.tv_stat_value).text = getString(R.string.reports_count)
        statReports.findViewById<TextView>(R.id.tv_stat_label).text = getString(R.string.stat_reports)

        val statFlagged = view.findViewById<View>(R.id.stat_card_flagged)
        statFlagged.findViewById<TextView>(R.id.tv_stat_value).text = getString(R.string.flagged_count)
        statFlagged.findViewById<TextView>(R.id.tv_stat_label).text = getString(R.string.stat_flagged)

        val statAccuracy = view.findViewById<View>(R.id.stat_card_accuracy)
        statAccuracy.findViewById<TextView>(R.id.tv_stat_value).text = getString(R.string.ai_accuracy)
        statAccuracy.findViewById<TextView>(R.id.tv_stat_label).text = getString(R.string.stat_accuracy)
    }

    private fun setupQuickChips(view: View) {
        val chipData = listOf(
            Triple(R.id.chip_lab, R.drawable.ic_lab, R.string.chip_lab),
            Triple(R.id.chip_ecg, R.drawable.ic_ecg, R.string.chip_ecg),
            Triple(R.id.chip_mri, R.drawable.ic_mri, R.string.chip_mri),
            Triple(R.id.chip_xray, R.drawable.ic_xray, R.string.chip_xray),
            Triple(R.id.chip_retinal, R.drawable.ic_retinal, R.string.chip_retinal),
            Triple(R.id.chip_prescription, R.drawable.ic_prescription, R.string.chip_prescription)
        )

        chipData.forEach { (viewId, iconRes, labelRes) ->
            val chip = view.findViewById<View>(viewId)
            chip.findViewById<ImageView>(R.id.iv_chip_icon).setImageResource(iconRes)
            chip.findViewById<TextView>(R.id.tv_chip_label).text = getString(labelRes)
        }
    }

    private fun setupRecentReports(view: View) {
        val sampleReports = listOf(
            Report(1, "CBC Blood Test", "Mar 10, 2025", "City Medical Center", "Normal", R.drawable.ic_lab),
            Report(2, "Chest X-Ray", "Mar 08, 2025", "General Hospital", "Review", R.drawable.ic_xray),
            Report(3, "ECG Reading", "Mar 05, 2025", "Heart Clinic", "Clear", R.drawable.ic_ecg)
        )

        val reportViews = listOf(
            view.findViewById<View>(R.id.recent_report_1),
            view.findViewById<View>(R.id.recent_report_2),
            view.findViewById<View>(R.id.recent_report_3)
        )

        sampleReports.forEachIndexed { index, report ->
            val reportView = reportViews[index]
            reportView.findViewById<ImageView>(R.id.iv_report_icon).setImageResource(report.iconResId)
            reportView.findViewById<TextView>(R.id.tv_report_name).text = report.name
            reportView.findViewById<TextView>(R.id.tv_report_date).text = report.date
            val tvStatus = reportView.findViewById<TextView>(R.id.tv_status_badge)
            tvStatus.text = report.status
            val badgeDrawable = when (report.status.lowercase()) {
                "normal" -> R.drawable.bg_badge_normal
                "review" -> R.drawable.bg_badge_review
                "watch"  -> R.drawable.bg_badge_watch
                "clear"  -> R.drawable.bg_badge_clear
                else     -> R.drawable.bg_badge_normal
            }
            tvStatus.setBackgroundResource(badgeDrawable)
        }
    }
}
