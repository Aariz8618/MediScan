package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PrescriptionFragment : BaseDualViewFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_prescription, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            AnalysisResult.patientReport.ifEmpty { "No prescription report available." }

        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            AnalysisResult.doctorReport.ifEmpty { "No clinical report available." }
    }
}
