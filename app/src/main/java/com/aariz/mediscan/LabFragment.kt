package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LabFragment : BaseDualViewFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_lab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        val result = AnalysisResult

        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            result.patientReport.ifEmpty { "No report available. Please upload a lab report." }

        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            result.doctorReport.ifEmpty { "No clinical report available." }

        view.findViewById<TextView>(R.id.tvModelInfo)?.apply {
            if (result.modelUsed.isNotEmpty()) {
                text = "Model: ${result.modelUsed}" +
                        if (result.confidence > 0) " • ${"%.1f".format(result.confidence * 100)}%" else ""
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }
}