package com.aariz.mediscan

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("medscan_prefs", Context.MODE_PRIVATE)

    var fullName: String
        get() = prefs.getString("name", "") ?: ""
        set(v) = prefs.edit().putString("name", v).apply()

    var phone: String
        get() = prefs.getString("phone", "") ?: ""
        set(v) = prefs.edit().putString("phone", v).apply()

    var age: String
        get() = prefs.getString("age", "") ?: ""
        set(v) = prefs.edit().putString("age", v).apply()

    var sex: String
        get() = prefs.getString("sex", "") ?: ""
        set(v) = prefs.edit().putString("sex", v).apply()

    var bloodGroup: String
        get() = prefs.getString("blood", "") ?: ""
        set(v) = prefs.edit().putString("blood", v).apply()

    var city: String
        get() = prefs.getString("city", "") ?: ""
        set(v) = prefs.edit().putString("city", v).apply()

    var totalReports: Int
        get() = prefs.getInt("total_reports", 0)
        set(v) = prefs.edit().putInt("total_reports", v).apply()

    var flaggedReports: Int
        get() = prefs.getInt("flagged_reports", 0)
        set(v) = prefs.edit().putInt("flagged_reports", v).apply()

    var joinTimestamp: Long
        get() = prefs.getLong("join_ts", 0L)
        set(v) = prefs.edit().putLong("join_ts", v).apply()

    var conditions: MutableList<String>
        get() = jsonToList("conditions")
        set(v) = listToJson("conditions", v)

    var medications: MutableList<String>
        get() = jsonToList("medications")
        set(v) = listToJson("medications", v)

    var allergies: MutableList<String>
        get() = jsonToList("allergies")
        set(v) = listToJson("allergies", v)

    var notificationsEnabled: Boolean
        get() = prefs.getBoolean("notifs", true)
        set(v) = prefs.edit().putBoolean("notifs", v).apply()

    fun initial(): String {
        val n = fullName.trim()
        return if (n.isNotEmpty()) n[0].uppercaseChar().toString() else "U"
    }

    fun memberLabel(): String {
        if (joinTimestamp == 0L) return "0mo"
        val months = ((System.currentTimeMillis() - joinTimestamp) /
                (1000L * 60 * 60 * 24 * 30)).coerceAtLeast(0)
        return if (months >= 12) "${months / 12}yr" else "${months}mo"
    }

    private fun jsonToList(key: String): MutableList<String> {
        val raw = prefs.getString(key, "[]") ?: "[]"
        return try {
            val arr = JSONArray(raw)
            MutableList(arr.length()) { arr.getString(it) }
        } catch (e: Exception) { mutableListOf() }
    }

    private fun listToJson(key: String, list: List<String>) {
        prefs.edit().putString(key, JSONArray(list).toString()).apply()
    }
}