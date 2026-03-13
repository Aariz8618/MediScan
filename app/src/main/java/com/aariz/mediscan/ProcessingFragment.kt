package com.aariz.mediscan

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProcessingFragment : Fragment() {

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        i.inflate(R.layout.fragment_processing, c, false)

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        // Back → Upload
        v.findViewById<View>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // View Results button
        v.findViewById<View>(R.id.btnViewResults).setOnClickListener {
            goToResults()
        }

        // Auto-navigate after 3 seconds (remove when real AI is wired)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded) goToResults()
        }, 3000)
    }

    private fun goToResults() {
        (activity as? MainActivity)?.navigateTo(LabResultFragment(), "lab_result")
    }
}