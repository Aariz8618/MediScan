package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// ─────────────────────────────────────────────────────────────
// MriFragment
// ─────────────────────────────────────────────────────────────
class MriFragment : BaseDualViewFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_mri, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            AnalysisResult.patientReport.ifEmpty { "No MRI report available." }
        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            AnalysisResult.doctorReport.ifEmpty { "No clinical report available." }
        if (AnalysisResult.modelUsed.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvModelInfo)?.text =
                "Model: ${AnalysisResult.modelUsed}" +
                if (AnalysisResult.confidence > 0) " • ${"%.1f".format(AnalysisResult.confidence * 100)}%" else ""
        }
    }
}

// ─────────────────────────────────────────────────────────────
// EcgFragment
// ─────────────────────────────────────────────────────────────
class EcgFragment : BaseDualViewFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_ecg, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            AnalysisResult.patientReport.ifEmpty { "No ECG report available." }
        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            AnalysisResult.doctorReport.ifEmpty { "No clinical report available." }
        if (AnalysisResult.modelUsed.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvModelInfo)?.text =
                "Model: ${AnalysisResult.modelUsed}" +
                if (AnalysisResult.confidence > 0) " • ${"%.1f".format(AnalysisResult.confidence * 100)}%" else ""
        }
    }
}

// ─────────────────────────────────────────────────────────────
// CtFragment
// ─────────────────────────────────────────────────────────────
class CtFragment : BaseDualViewFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_ct, container, false)

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
        if (AnalysisResult.modelUsed.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvModelInfo)?.text =
                "Model: ${AnalysisResult.modelUsed}" +
                if (AnalysisResult.confidence > 0) " • ${"%.1f".format(AnalysisResult.confidence * 100)}%" else ""
        }
    }
}

// ─────────────────────────────────────────────────────────────
// XrayFragment
// ─────────────────────────────────────────────────────────────
class XrayFragment : BaseDualViewFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_xray, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            AnalysisResult.patientReport.ifEmpty { "No X-Ray report available." }
        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            AnalysisResult.doctorReport.ifEmpty { "No clinical report available." }
        if (AnalysisResult.modelUsed.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvModelInfo)?.text =
                "Model: ${AnalysisResult.modelUsed}" +
                if (AnalysisResult.confidence > 0) " • ${"%.1f".format(AnalysisResult.confidence * 100)}%" else ""
        }
    }
}

// ─────────────────────────────────────────────────────────────
// RetinalFragment
// ─────────────────────────────────────────────────────────────
class RetinalFragment : BaseDualViewFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_retinal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDualView(view)
        populateFromApi(view)
    }

    private fun populateFromApi(view: View) {
        view.findViewById<TextView>(R.id.tvPatientReport)?.text =
            AnalysisResult.patientReport.ifEmpty { "No retinal scan report available." }
        view.findViewById<TextView>(R.id.tvDoctorReport)?.text =
            AnalysisResult.doctorReport.ifEmpty { "No clinical report available." }
        if (AnalysisResult.modelUsed.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvModelInfo)?.text =
                "Model: ${AnalysisResult.modelUsed}" +
                if (AnalysisResult.confidence > 0) " • ${"%.1f".format(AnalysisResult.confidence * 100)}%" else ""
        }
    }
}

// ─────────────────────────────────────────────────────────────
// PrescriptionFragment
// ─────────────────────────────────────────────────────────────
class PrescriptionFragment : BaseDualViewFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_prescription, container, false)

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
