package com.aariz.mediscan

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Shared singleton that holds the latest API result.
 * ProcessingFragment writes here → Result fragments read from here.
 */
object AnalysisResult {
    private const val PREFS = "mediscan_prefs"
    private const val KEY_LATEST = "latest_result"
    private const val KEY_HISTORY = "report_history"
    private const val MAX_HISTORY_ITEMS = 100
    private val gson = Gson()

    data class StoredResult(
        val id: String = UUID.randomUUID().toString(),
        val reportType: String,
        val patientReport: String,
        val doctorReport: String,
        val modelUsed: String,
        val confidence: Float,
        val patientName: String,
        val createdAt: Long = System.currentTimeMillis()
    ) {
        val displayDate: String
            get() = SimpleDateFormat("MMM d · h:mm a", Locale.getDefault()).format(Date(createdAt))
    }

    var patientReport: String = ""
    var doctorReport: String  = ""
    var reportType: String    = ""
    var modelUsed: String     = ""
    var confidence: Float     = 0f
    var patientName: String   = ""

    fun saveLatestAndHistory(context: Context) {
        val current = StoredResult(
            reportType = reportType,
            patientReport = patientReport,
            doctorReport = doctorReport,
            modelUsed = modelUsed,
            confidence = confidence,
            patientName = patientName
        )
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val existing = getHistory(context).toMutableList()
        existing.add(0, current)
        if (existing.size > MAX_HISTORY_ITEMS) {
            existing.subList(MAX_HISTORY_ITEMS, existing.size).clear()
        }

        prefs.edit()
            .putString(KEY_LATEST, gson.toJson(current))
            .putString(KEY_HISTORY, gson.toJson(existing))
            .apply()
    }

    fun loadLatest(context: Context): Boolean {
        val json = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_LATEST, null)
            ?: return false
        return runCatching {
            val stored = gson.fromJson(json, StoredResult::class.java)
            patientReport = stored.patientReport
            doctorReport = stored.doctorReport
            reportType = stored.reportType
            modelUsed = stored.modelUsed
            confidence = stored.confidence
            patientName = stored.patientName
            true
        }.getOrDefault(false)
    }

    fun loadByType(context: Context, type: String): Boolean {
        val stored = getHistory(context).firstOrNull { it.reportType == type } ?: return false
        patientReport = stored.patientReport
        doctorReport = stored.doctorReport
        reportType = stored.reportType
        modelUsed = stored.modelUsed
        confidence = stored.confidence
        patientName = stored.patientName
        return true
    }

    fun getHistory(context: Context): List<StoredResult> {
        val json = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_HISTORY, null)
            ?: return emptyList()
        return runCatching {
            val type = object : TypeToken<List<StoredResult>>() {}.type
            gson.fromJson<List<StoredResult>>(json, type).orEmpty()
        }.getOrDefault(emptyList())
    }

    fun clear() {
        patientReport = ""
        doctorReport  = ""
        reportType    = ""
        modelUsed     = ""
        confidence    = 0f
        patientName   = ""
    }
}
