package com.aariz.mediscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.mediscan.R
import com.aariz.mediscan.model.Report

class ReportAdapter(
    private val reports: List<Report>,
    private val showHospital: Boolean = false,
    private val onItemClick: ((Report) -> Unit)? = null
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.iv_report_icon)
        val tvName: TextView = view.findViewById(R.id.tv_report_name)
        val tvDate: TextView = view.findViewById(R.id.tv_report_date)
        val tvHospital: TextView = view.findViewById(R.id.tv_hospital_name)
        val tvStatus: TextView = view.findViewById(R.id.tv_status_badge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.report_card, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reports[position]
        holder.ivIcon.setImageResource(report.iconResId)
        holder.tvName.text = report.name
        holder.tvDate.text = report.date

        if (showHospital && report.hospitalName.isNotEmpty()) {
            holder.tvHospital.visibility = View.VISIBLE
            holder.tvHospital.text = report.hospitalName
        } else {
            holder.tvHospital.visibility = View.GONE
        }

        holder.tvStatus.text = report.status
        val badgeDrawable = when (report.status.lowercase()) {
            "normal" -> R.drawable.bg_badge_normal
            "review" -> R.drawable.bg_badge_review
            "watch"  -> R.drawable.bg_badge_watch
            "clear"  -> R.drawable.bg_badge_clear
            else     -> R.drawable.bg_badge_normal
        }
        holder.tvStatus.setBackgroundResource(badgeDrawable)

        holder.itemView.setOnClickListener { onItemClick?.invoke(report) }
    }

    override fun getItemCount(): Int = reports.size
}
