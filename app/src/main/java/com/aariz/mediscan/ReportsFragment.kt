package com.aariz.mediscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.aariz.mediscan.adapter.ReportsAdapter
import com.aariz.mediscan.model.RType
import com.aariz.mediscan.ui.ReportsVM

class ReportsFragment : Fragment() {

    private val vm: ReportsVM by viewModels()
    private val adapter = ReportsAdapter { report ->
        // TODO: navigate to report detail screen
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        i.inflate(R.layout.fragment_reports, c, false)

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        // ── Back button ───────────────────────────────────────
        v.findViewById<View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // ── RecyclerView ──────────────────────────────────────
        v.findViewById<RecyclerView>(R.id.rvReports).adapter = adapter

        // ── Filter tabs ───────────────────────────────────────
        val tabs = listOf(
            v.findViewById<TextView>(R.id.tabAll),
            v.findViewById(R.id.tabLab),
            v.findViewById(R.id.tabImg),
            v.findViewById(R.id.tabEcg)
        )

        fun selectTab(index: Int, filter: () -> Unit) {
            tabs.forEachIndexed { i, tab ->
                val on = i == index
                tab.background = ContextCompat.getDrawable(
                    requireContext(),
                    if (on) R.drawable.bg_tab_on else R.drawable.bg_tab_off
                )
                tab.setTextColor(ContextCompat.getColor(
                    requireContext(),
                    if (on) android.R.color.white else R.color.ic_muted
                ))
            }
            filter()
        }

        tabs[0].setOnClickListener { selectTab(0) { vm.filterAll() } }
        tabs[1].setOnClickListener { selectTab(1) { vm.filter(RType.LAB) } }
        tabs[2].setOnClickListener { selectTab(2) { vm.filterImaging() } }
        tabs[3].setOnClickListener { selectTab(3) { vm.filter(RType.ECG) } }

        // ── Observe list ──────────────────────────────────────
        vm.list.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            v.findViewById<View>(R.id.emptyState).visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
            v.findViewById<View>(R.id.rvReports).visibility =
                if (list.isEmpty()) View.GONE else View.VISIBLE
        }

        // ── Observe counts ────────────────────────────────────
        vm.counts.observe(viewLifecycleOwner) { (total, flagged) ->
            v.findViewById<TextView>(R.id.tvSub).text =
                "$total reports · $flagged need review"
        }
    }
}