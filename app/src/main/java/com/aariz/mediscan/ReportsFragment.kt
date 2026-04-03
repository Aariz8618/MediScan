package com.aariz.mediscan;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ReportsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_reports, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Filter tabs
        val tabAll = view.findViewById<TextView>(R.id.tabAll)
        val tabLab = view.findViewById<TextView>(R.id.tabLab)
        val tabImaging = view.findViewById<TextView>(R.id.tabImaging)
        val tabEcg = view.findViewById<TextView>(R.id.tabEcg)
        val tabs = listOf(tabAll, tabLab, tabImaging, tabEcg)

        fun setActiveTab(active: TextView) {
            tabs.forEach { it.setBackgroundResource(0) }
            active.setBackgroundResource(R.drawable.bg_tab_active)
        }
        tabs.forEach { tab ->
            tab.setOnClickListener { setActiveTab(tab) }
        }

        // Report cards → open detail screens
        view.findViewById<LinearLayout>(R.id.cardCbc).setOnClickListener {
            navigateTo(LabFragment())
        }
        view.findViewById<LinearLayout>(R.id.cardEcg).setOnClickListener {
            navigateTo(EcgFragment())
        }
        view.findViewById<LinearLayout>(R.id.cardMri).setOnClickListener {
            navigateTo(MriFragment())
        }
        view.findViewById<LinearLayout>(R.id.cardXray).setOnClickListener {
            navigateTo(XrayFragment())
        }
        view.findViewById<LinearLayout>(R.id.cardCt).setOnClickListener {
            navigateTo(CtFragment())
        }
        view.findViewById<LinearLayout>(R.id.cardRetinal).setOnClickListener {
            navigateTo(RetinalFragment())
        }
        view.findViewById<LinearLayout>(R.id.cardRx).setOnClickListener {
            navigateTo(PrescriptionFragment())
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
