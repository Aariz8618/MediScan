package com.aariz.mediscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aariz.mediscan.R

class AnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_analysis, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewToggle(view)
        setupAnalysisCards(view)
    }

    private fun setupViewToggle(view: View) {
        val tabPatient = view.findViewById<TextView>(R.id.tab_patient)
        val tabDoctor = view.findViewById<TextView>(R.id.tab_doctor)
        val containerPatient = view.findViewById<View>(R.id.container_patient)
        val containerDoctor = view.findViewById<View>(R.id.container_doctor)

        tabPatient.setOnClickListener {
            tabPatient.setBackgroundResource(R.drawable.bg_button_primary)
            tabPatient.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tabDoctor.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            tabDoctor.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_hint))
            containerPatient.visibility = View.VISIBLE
            containerDoctor.visibility = View.GONE
        }

        tabDoctor.setOnClickListener {
            tabDoctor.setBackgroundResource(R.drawable.bg_button_primary)
            tabDoctor.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tabPatient.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            tabPatient.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_hint))
            containerPatient.visibility = View.GONE
            containerDoctor.visibility = View.VISIBLE
        }
    }

    private fun setupAnalysisCards(view: View) {
        // Patient view: AI explanation card
        val cardExplanation = view.findViewById<View>(R.id.card_ai_explanation)
        cardExplanation.findViewById<TextView>(R.id.tv_card_title).text = getString(R.string.ai_explanation)
        cardExplanation.findViewById<TextView>(R.id.tv_card_content).text =
            "Your Complete Blood Count (CBC) results show that your hemoglobin level is slightly below the normal range, " +
            "which may indicate mild anaemia. Your white blood cell count is within normal limits, suggesting no active infection. " +
            "Overall the report looks stable — a follow-up in 4 weeks is recommended."

        // Doctor view: Clinical Metrics card
        val cardClinical = view.findViewById<View>(R.id.card_clinical)
        cardClinical.findViewById<TextView>(R.id.tv_card_title).text = getString(R.string.clinical_metrics)
        cardClinical.findViewById<TextView>(R.id.tv_card_content).text =
            "Hb: 10.8 g/dL (ref: 12–17)\n" +
            "WBC: 7.2 × 10³/µL (ref: 4–11)\n" +
            "Platelets: 210 × 10³/µL (ref: 150–400)\n" +
            "MCV: 74 fL (ref: 80–100) — microcytic pattern"

        // Doctor view: AI Reasoning card
        val cardReasoning = view.findViewById<View>(R.id.card_reasoning)
        cardReasoning.findViewById<TextView>(R.id.tv_card_title).text = getString(R.string.ai_reasoning)
        cardReasoning.findViewById<TextView>(R.id.tv_card_content).text =
            "Model detected microcytic hypochromic pattern consistent with iron-deficiency anaemia. " +
            "Confidence: 92%. Differential includes thalassaemia trait (less likely given clinical history). " +
            "Serum ferritin and peripheral smear correlation advised."

        // Doctor view: Recommendations card
        val cardRecommendations = view.findViewById<View>(R.id.card_recommendations)
        cardRecommendations.findViewById<TextView>(R.id.tv_card_title).text = getString(R.string.recommendations)
        cardRecommendations.findViewById<TextView>(R.id.tv_card_content).text =
            "1. Serum ferritin, TIBC, serum iron panel\n" +
            "2. Dietary iron counselling\n" +
            "3. Consider oral iron supplementation\n" +
            "4. Repeat CBC in 4–6 weeks"
    }
}
