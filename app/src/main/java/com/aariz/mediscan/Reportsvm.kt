package com.aariz.mediscan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.aariz.mediscan.model.*
import com.aariz.mediscan.model.RStatus.*
import com.aariz.mediscan.model.RType.*

class ReportsVM : ViewModel() {

    private val _all = MutableLiveData<List<Report>>(emptyList())
    private val _filter = MutableLiveData("ALL")

    val list: LiveData<List<Report>> = _filter.combine(_all) { f, all ->
        when (f) {
            "LAB"     -> all.filter { it.type == LAB }
            "ECG"     -> all.filter { it.type == ECG }
            "IMAGING" -> all.filter { it.type in listOf(MRI, XRAY, CT, EYE) }
            else      -> all
        }
    }

    val counts: LiveData<Pair<Int, Int>> = _all.map { all ->
        val flagged = all.count { it.status in listOf(REVIEW, NOTE, WATCH, FLAG) }
        all.size to flagged
    }

    fun filterAll()      { _filter.value = "ALL" }
    fun filter(t: RType) { _filter.value = t.name }
    fun filterImaging()  { _filter.value = "IMAGING" }

    /** Called after AI model finishes analyzing the uploaded report */
    fun addReport(r: Report) {
        _all.value = listOf(r) + _all.value.orEmpty()
    }

    private fun <A, B, R> LiveData<A>.combine(other: LiveData<B>, fn: (A, B) -> R): LiveData<R> {
        val out = MutableLiveData<R>()
        val emit = { _: Any? ->
            val a = value; val b = other.value
            if (a != null && b != null) out.value = fn(a, b)
        }
        observeForever(emit)
        other.observeForever(emit)
        return out
    }
}