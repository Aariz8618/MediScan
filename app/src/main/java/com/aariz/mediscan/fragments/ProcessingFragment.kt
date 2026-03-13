package com.aariz.mediscan.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aariz.mediscan.R

class ProcessingFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_processing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simulateProcessingSteps(view)
    }

    private fun simulateProcessingSteps(view: View) {
        // Step 1 already shown as complete (upload complete)
        // Animate step 2 → 3 → 4 → 5 with delays
        handler.postDelayed({
            if (!isAdded) return@postDelayed
            activateStep(view, R.id.step_detect)
            completeStep(view, R.id.step_ocr)
        }, 1500L)

        handler.postDelayed({
            if (!isAdded) return@postDelayed
            activateStep(view, R.id.step_reason)
            completeStep(view, R.id.step_detect)
        }, 3000L)

        handler.postDelayed({
            if (!isAdded) return@postDelayed
            activateStep(view, R.id.step_explain)
            completeStep(view, R.id.step_reason)
        }, 4500L)

        handler.postDelayed({
            if (!isAdded) return@postDelayed
            completeStep(view, R.id.step_explain)
            // Navigate to analysis result
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AnalysisFragment())
                .addToBackStack(null)
                .commit()
        }, 6000L)
    }

    private fun activateStep(view: View, stepId: Int) {
        val step = view.findViewById<View>(stepId) ?: return
        step.alpha = 1.0f
    }

    private fun completeStep(view: View, stepId: Int) {
        val step = view.findViewById<View>(stepId) ?: return
        step.alpha = 1.0f
        // Hide any progress bar within this step and show check icon
        step.findViewWithTag<ProgressBar?>("progress_step")?.visibility = View.GONE
        step.findViewWithTag<ImageView?>("icon_step")?.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_check)
            imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.bright_accent)
        }
        // For step_ocr specifically, hide its progress bar by ID
        step.findViewById<ProgressBar?>(R.id.progress_ocr)?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
