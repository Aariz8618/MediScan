package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class LabResultFragment : Fragment() {

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        i.inflate(R.layout.fragment_lab_result, c, false)

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        // Back → previous screen
        v.findViewById<View>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Tabs
        val tabSummary    = v.findViewById<TextView>(R.id.tabSummary)
        val tabAllValues  = v.findViewById<TextView>(R.id.tabAllValues)
        val paneSummary   = v.findViewById<View>(R.id.paneSummary)
        val paneAllValues = v.findViewById<View>(R.id.paneAllValues)

        fun selectTab(showSummary: Boolean) {
            tabSummary.background = ContextCompat.getDrawable(requireContext(),
                if (showSummary) R.drawable.bg_tab_on else R.drawable.bg_tab_off)
            tabSummary.setTextColor(ContextCompat.getColor(requireContext(),
                if (showSummary) android.R.color.white else R.color.ic_muted))

            tabAllValues.background = ContextCompat.getDrawable(requireContext(),
                if (!showSummary) R.drawable.bg_tab_on else R.drawable.bg_tab_off)
            tabAllValues.setTextColor(ContextCompat.getColor(requireContext(),
                if (!showSummary) android.R.color.white else R.color.ic_muted))

            paneSummary.visibility   = if (showSummary)  View.VISIBLE else View.GONE
            paneAllValues.visibility = if (!showSummary) View.VISIBLE else View.GONE
        }

        tabSummary.setOnClickListener   { selectTab(true)  }
        tabAllValues.setOnClickListener { selectTab(false) }
        selectTab(false) // default: All Values

        // Save to Reports
        v.findViewById<View>(R.id.btnSave).setOnClickListener {
            // TODO: wire to ReportsVM.addReport(report)
            // HomeFragment.incrementReportStats(requireContext(), 98)
            Toast.makeText(requireContext(), "Report saved!", Toast.LENGTH_SHORT).show()

            // Safe pop — works for both Upload flow AND Try Sample flow
            val fm = requireActivity().supportFragmentManager
            val hasUploadTag = fm.findFragmentByTag("upload") != null
            if (hasUploadTag) {
                fm.popBackStack("upload",
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                fm.popBackStack() // Try Sample flow — just go back one step
            }
        }
    }
}