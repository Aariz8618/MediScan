package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AnalysisFragment : Fragment() {

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        i.inflate(R.layout.fragment_analysis, c, false)

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        // Upload a Report → UploadFragment
        v.findViewById<View>(R.id.btnUpload).setOnClickListener {
            (activity as? MainActivity)?.navigateTo(UploadFragment(), "upload")
        }

        // Try Sample → LabResultFragment (demo)
        v.findViewById<View>(R.id.btnSample).setOnClickListener {
            (activity as? MainActivity)?.navigateTo(LabResultFragment(), "lab_result")
        }
    }
}