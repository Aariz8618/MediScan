package com.aariz.mediscan;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ReportsFragment : Fragment() {

    private enum class Filter { ALL, LAB, IMAGING, ECG }
    private var availableTypes: Set<String> = emptySet()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_reports, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabAll = view.findViewById<TextView>(R.id.tabAll)
        val tabLab = view.findViewById<TextView>(R.id.tabLab)
        val tabImaging = view.findViewById<TextView>(R.id.tabImaging)
        val tabEcg = view.findViewById<TextView>(R.id.tabEcg)
        val tabs = listOf(tabAll, tabLab, tabImaging, tabEcg)
        val cardMap = mapOf(
            "lab" to view.findViewById<LinearLayout>(R.id.cardCbc),
            "ecg" to view.findViewById<LinearLayout>(R.id.cardEcg),
            "brain_mri" to view.findViewById<LinearLayout>(R.id.cardMri),
            "chest_xray" to view.findViewById<LinearLayout>(R.id.cardXray),
            "lung_ct" to view.findViewById<LinearLayout>(R.id.cardCt),
            "retinal" to view.findViewById<LinearLayout>(R.id.cardRetinal),
            "prescription" to view.findViewById<LinearLayout>(R.id.cardRx)
        )

        fun setActiveTab(active: TextView, filter: Filter) {
            tabs.forEach {
                it.setBackgroundResource(0)
                it.setTextColor(requireContext().getColor(R.color.muted))
            }
            active.setBackgroundResource(R.drawable.bg_tab_active)
            active.setTextColor(requireContext().getColor(R.color.hdr))
            applyFilter(view, cardMap, filter)
        }

        tabAll.setOnClickListener { setActiveTab(tabAll, Filter.ALL) }
        tabLab.setOnClickListener { setActiveTab(tabLab, Filter.LAB) }
        tabImaging.setOnClickListener { setActiveTab(tabImaging, Filter.IMAGING) }
        tabEcg.setOnClickListener { setActiveTab(tabEcg, Filter.ECG) }

        cardMap["lab"]?.setOnClickListener {
            openByType("lab", LabFragment())
        }
        cardMap["ecg"]?.setOnClickListener {
            openByType("ecg", EcgFragment())
        }
        cardMap["brain_mri"]?.setOnClickListener {
            openByType("brain_mri", MriFragment())
        }
        cardMap["chest_xray"]?.setOnClickListener {
            openByType("chest_xray", XrayFragment())
        }
        cardMap["lung_ct"]?.setOnClickListener {
            openByType("lung_ct", CtFragment())
        }
        cardMap["retinal"]?.setOnClickListener {
            openByType("retinal", RetinalFragment())
        }
        cardMap["prescription"]?.setOnClickListener {
            openByType("prescription", PrescriptionFragment())
        }

        view.findViewById<EditText>(R.id.searchBar)?.isEnabled = false
        renderHistoryVisibility(cardMap)
        setActiveTab(tabAll, Filter.ALL)
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun renderHistoryVisibility(cardMap: Map<String, LinearLayout>) {
        availableTypes = AnalysisResult.getHistory(requireContext().applicationContext).map { it.reportType }.toSet()
        cardMap.forEach { (type, card) ->
            card.visibility = if (availableTypes.contains(type)) View.VISIBLE else View.GONE
        }
        if (availableTypes.isEmpty()) {
            cardMap["lab"]?.visibility = View.VISIBLE
            cardMap["ecg"]?.visibility = View.VISIBLE
            cardMap["brain_mri"]?.visibility = View.VISIBLE
            availableTypes = setOf("lab", "ecg", "brain_mri")
        }
    }

    private fun applyFilter(view: View, cardMap: Map<String, LinearLayout>, filter: Filter) {
        val showSet = when (filter) {
            Filter.ALL -> availableTypes
            Filter.LAB -> availableTypes.filter { it == "lab" || it == "prescription" }.toSet()
            Filter.IMAGING -> availableTypes.filter { it in setOf("brain_mri", "chest_xray", "lung_ct", "retinal") }.toSet()
            Filter.ECG -> availableTypes.filter { it == "ecg" }.toSet()
        }
        cardMap.forEach { (type, card) ->
            card.visibility = if (showSet.contains(type)) View.VISIBLE else View.GONE
        }
        view.findViewById<TextView>(R.id.tabAll)?.contentDescription = "reports_filter_${filter.name.lowercase()}"
    }

    private fun openByType(type: String, fallback: Fragment) {
        AnalysisResult.loadByType(requireContext().applicationContext, type)
        navigateTo(fallback)
    }
}
