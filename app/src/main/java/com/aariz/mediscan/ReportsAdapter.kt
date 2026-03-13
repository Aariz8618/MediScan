package com.aariz.mediscan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aariz.mediscan.R
import com.aariz.mediscan.model.MColor
import com.aariz.mediscan.model.MColor.*
import com.aariz.mediscan.model.RStatus.*
import com.aariz.mediscan.model.RType.*
import com.aariz.mediscan.model.Report
import java.text.SimpleDateFormat
import java.util.*

class ReportsAdapter(
    private val onClick: (Report) -> Unit
) : ListAdapter<Report, ReportsAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Report>() {
            override fun areItemsTheSame(a: Report, b: Report) = a.id == b.id
            override fun areContentsTheSame(a: Report, b: Report) = a == b
        }
        private val FMT_TIME = SimpleDateFormat("h:mm a", Locale.getDefault())
        private val FMT_DATE = SimpleDateFormat("MMM d · h:mm a", Locale.getDefault())
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val iconWrap: FrameLayout = v.findViewById(R.id.iconWrap)
        val tvIcon:   TextView   = v.findViewById(R.id.tvIcon)
        val tvName:   TextView   = v.findViewById(R.id.tvName)
        val tvDate:   TextView   = v.findViewById(R.id.tvDate)
        val tvBadge:  TextView   = v.findViewById(R.id.tvBadge)
        val tvM1Val:  TextView   = v.findViewById(R.id.tvM1Val)
        val tvM1Lbl:  TextView   = v.findViewById(R.id.tvM1Lbl)
        val tvM2Val:  TextView   = v.findViewById(R.id.tvM2Val)
        val tvM2Lbl:  TextView   = v.findViewById(R.id.tvM2Lbl)
        val tvM3Val:  TextView   = v.findViewById(R.id.tvM3Val)
        val tvM3Lbl:  TextView   = v.findViewById(R.id.tvM3Lbl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun onBindViewHolder(h: VH, pos: Int) {
        val r   = getItem(pos)
        val ctx = h.itemView.context

        // Icon — use setBackgroundColor since all iconBg() return R.color
        h.tvIcon.text = r.type.emoji
        h.iconWrap.setBackgroundColor(ContextCompat.getColor(ctx, iconBg(r.type)))

        // Title + date
        h.tvName.text = r.title
        h.tvDate.text = buildDate(r.date, r.hospital)

        // Badge
        h.tvBadge.text = r.status.label
        h.tvBadge.background = ContextCompat.getDrawable(ctx, badgeBg(r.status))
        h.tvBadge.setTextColor(ContextCompat.getColor(ctx, badgeColor(r.status)))

        // Metrics
        h.tvM1Val.text = r.m1Val ; h.tvM1Val.setTextColor(color(ctx, r.m1Color))
        h.tvM1Lbl.text = r.m1Lbl
        h.tvM2Val.text = r.m2Val ; h.tvM2Val.setTextColor(color(ctx, r.m2Color))
        h.tvM2Lbl.text = r.m2Lbl
        h.tvM3Val.text = r.m3Val ; h.tvM3Val.setTextColor(color(ctx, r.m3Color))
        h.tvM3Lbl.text = r.m3Lbl

        h.itemView.setOnClickListener { onClick(r) }
    }

    // ── Helpers ───────────────────────────────────────────────

    private fun color(ctx: android.content.Context, c: MColor) =
        ContextCompat.getColor(ctx, when (c) {
            RED   -> R.color.c_red
            AMBER -> R.color.c_amber
            GREEN -> R.color.ic_green
            else  -> R.color.ic_txt
        })

    private fun buildDate(d: Date, hospital: String): String {
        val now = Calendar.getInstance()
        val cal = Calendar.getInstance().also { it.time = d }
        val isToday = cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
                && cal.get(Calendar.YEAR)        == now.get(Calendar.YEAR)
        val t = if (isToday) "Today · ${FMT_TIME.format(d)}" else FMT_DATE.format(d)
        return if (hospital.isBlank()) t else "$t · $hospital"
    }

    // All return R.color — used with setBackgroundColor()
    private fun iconBg(t: com.aariz.mediscan.model.RType) = when (t) {
        LAB   -> R.color.ic_lab_bg
        ECG   -> R.color.ic_ecg_bg
        MRI   -> R.color.ic_mri_bg
        XRAY  -> R.color.ic_xray_bg
        CT    -> R.color.ic_ct_bg
        EYE   -> R.color.ic_eye_bg
        RX    -> R.color.ic_rx_bg
        OTHER -> R.color.ic_lab_bg
    }

    private fun badgeBg(s: com.aariz.mediscan.model.RStatus) = when (s) {
        NORMAL, CLEAR, SAFE -> R.drawable.bg_bdg_green
        REVIEW, NOTE, WATCH -> R.drawable.bg_bdg_amber
        FLAG                -> R.drawable.bg_bdg_red
    }

    private fun badgeColor(s: com.aariz.mediscan.model.RStatus) = when (s) {
        NORMAL, CLEAR, SAFE -> R.color.ic_green
        REVIEW, NOTE, WATCH -> R.color.c_amber
        FLAG                -> R.color.c_red
    }
}