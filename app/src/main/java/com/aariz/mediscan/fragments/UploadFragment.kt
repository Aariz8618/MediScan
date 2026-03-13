package com.aariz.mediscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.mediscan.R

class UploadFragment : Fragment() {

    private var selectedTypeView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_upload, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupReportTypeSelector(view)
        setupAnalyzeButton(view)
    }

    private fun setupReportTypeSelector(view: View) {
        val typeIds = listOf(
            R.id.type_lab, R.id.type_ecg, R.id.type_mri, R.id.type_xray,
            R.id.type_ct, R.id.type_retinal, R.id.type_prescription, R.id.type_other
        )

        // Set Lab as default selected
        selectedTypeView = view.findViewById(R.id.type_lab)

        typeIds.forEach { typeId ->
            view.findViewById<View>(typeId).setOnClickListener { clickedView ->
                selectedTypeView?.setBackgroundResource(R.drawable.bg_type_chip)
                clickedView.setBackgroundResource(R.drawable.bg_type_chip_selected)
                selectedTypeView = clickedView
            }
        }
    }

    private fun setupAnalyzeButton(view: View) {
        view.findViewById<View>(R.id.btn_analyze).setOnClickListener {
            // Navigate to Processing screen
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProcessingFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
