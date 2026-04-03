package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CtFragment : BaseDualViewFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_ct, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            AnalysisResult.patientReport.ifEmpty { "No CT scan report available." }

        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            AnalysisResult.doctorReport.ifEmpty { "No clinical report available." }

        view.findViewById<TextView>(R.id.tvModelInfo)?.apply {
            if (AnalysisResult.modelUsed.isNotEmpty()) {
                text = "Model: ${AnalysisResult.modelUsed}" +
                        if (AnalysisResult.confidence > 0) " • ${"%.1f".format(AnalysisResult.confidence * 100)}%" else ""
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }
}
