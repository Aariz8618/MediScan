package com.aariz.mediscan;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

class ShareFragment : Fragment() {

    private var selectedDoctor = "Dr. R. Mehta"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_share, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back button
        view.findViewById<FrameLayout>(R.id.btnBack)?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Doctor selection
        val doctorMehta  = view.findViewById<LinearLayout>(R.id.doctorMehta)
        val doctorKapoor = view.findViewById<LinearLayout>(R.id.doctorKapoor)
        val doctorVerma  = view.findViewById<LinearLayout>(R.id.doctorVerma)

        fun selectDoctor(name: String, highlight: LinearLayout, others: List<LinearLayout>) {
            selectedDoctor = name
            highlight.setBackgroundColor(requireContext().getColor(R.color.g1))
            others.forEach { it.setBackgroundResource(0) }
            updateShareButton(view)
        }

        doctorMehta?.setOnClickListener {
            selectDoctor("Dr. R. Mehta", doctorMehta, listOf(doctorKapoor, doctorVerma))
        }
        doctorKapoor?.setOnClickListener {
            selectDoctor("Dr. S. Kapoor", doctorKapoor, listOf(doctorMehta, doctorVerma))
        }
        doctorVerma?.setOnClickListener {
            selectDoctor("Dr. A. Verma", doctorVerma, listOf(doctorMehta, doctorKapoor))
        }

        // Checkbox listeners update share button text
        view.findViewById<CheckBox>(R.id.checkCbc)?.setOnCheckedChangeListener { _, _ -> updateShareButton(view) }
        view.findViewById<CheckBox>(R.id.checkEcg)?.setOnCheckedChangeListener { _, _ -> updateShareButton(view) }
        view.findViewById<CheckBox>(R.id.checkXray)?.setOnCheckedChangeListener { _, _ -> updateShareButton(view) }
        view.findViewById<CheckBox>(R.id.checkMri)?.setOnCheckedChangeListener { _, _ -> updateShareButton(view) }

        // Share button
        view.findViewById<Button>(R.id.btnShareReports)?.setOnClickListener {
            val count = countChecked(view)
            Toast.makeText(
                requireContext(),
                "✓ $count report(s) shared securely with $selectedDoctor",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun countChecked(view: View): Int {
        val ids = listOf(R.id.checkCbc, R.id.checkEcg, R.id.checkXray, R.id.checkMri)
        return ids.count { view.findViewById<CheckBox>(it)?.isChecked == true }
    }

    private fun updateShareButton(view: View) {
        val count = countChecked(view)
        val label = if (count == 0) "Select at least one report"
                    else "Share $count Report${if (count > 1) "s" else ""} with $selectedDoctor"
        view.findViewById<Button>(R.id.btnShareReports)?.text = label
    }
}
