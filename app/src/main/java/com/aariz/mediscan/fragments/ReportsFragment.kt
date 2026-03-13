package com.aariz.mediscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.mediscan.R
import com.aariz.mediscan.adapters.ReportAdapter
import com.aariz.mediscan.model.Report

class ReportsFragment : Fragment() {

    private val allReports = listOf(
        Report(1, "CBC Blood Test", "Mar 10, 2025", "City Medical Center", "Normal", R.drawable.ic_lab),
        Report(2, "Chest X-Ray", "Mar 08, 2025", "General Hospital", "Review", R.drawable.ic_xray),
        Report(3, "ECG Reading", "Mar 05, 2025", "Heart Clinic", "Clear", R.drawable.ic_ecg),
        Report(4, "MRI Brain", "Mar 01, 2025", "Neuro Center", "Watch", R.drawable.ic_mri),
        Report(5, "Retinal Scan", "Feb 28, 2025", "Eye Care Hospital", "Normal", R.drawable.ic_retinal),
        Report(6, "Prescription", "Feb 25, 2025", "Family Clinic", "Clear", R.drawable.ic_prescription),
        Report(7, "Thyroid Panel", "Feb 22, 2025", "Lab Express", "Review", R.drawable.ic_lab),
        Report(8, "CT Abdomen", "Feb 18, 2025", "Radiology Plus", "Normal", R.drawable.ic_ct)
    )

    private lateinit var adapter: ReportAdapter
    private var currentFilter = "All"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_reports, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_reports)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ReportAdapter(allReports, showHospital = true)
        recyclerView.adapter = adapter

        setupFilterTabs(view)
    }

    private fun setupFilterTabs(view: View) {
        val tabs = mapOf(
            R.id.tab_all to "All",
            R.id.tab_lab to "Lab",
            R.id.tab_imaging to "Imaging",
            R.id.tab_ecg to "ECG"
        )

        tabs.forEach { (tabId, filter) ->
            view.findViewById<TextView>(tabId).setOnClickListener {
                currentFilter = filter
                updateTabAppearance(view, tabId)
                filterReports(filter)
            }
        }
    }

    private fun updateTabAppearance(view: View, selectedTabId: Int) {
        val tabIds = listOf(R.id.tab_all, R.id.tab_lab, R.id.tab_imaging, R.id.tab_ecg)
        tabIds.forEach { tabId ->
            val tab = view.findViewById<TextView>(tabId)
            if (tabId == selectedTabId) {
                tab.setBackgroundResource(R.drawable.bg_chip)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_teal))
            } else {
                tab.setBackgroundResource(R.drawable.bg_search_bar)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_hint))
            }
        }
    }

    private fun filterReports(filter: String) {
        val filtered = when (filter) {
            "Lab"     -> allReports.filter { it.iconResId == R.drawable.ic_lab || it.iconResId == R.drawable.ic_prescription }
            "Imaging" -> allReports.filter { it.iconResId == R.drawable.ic_xray || it.iconResId == R.drawable.ic_mri || it.iconResId == R.drawable.ic_ct || it.iconResId == R.drawable.ic_retinal }
            "ECG"     -> allReports.filter { it.iconResId == R.drawable.ic_ecg }
            else      -> allReports
        }
        adapter = ReportAdapter(filtered, showHospital = true)
        view?.findViewById<RecyclerView>(R.id.rv_reports)?.adapter = adapter
    }
}
