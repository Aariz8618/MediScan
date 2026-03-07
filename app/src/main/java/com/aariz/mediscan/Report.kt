package com.aariz.mediscan.model

import java.util.Date

enum class RType(val emoji: String) {
    LAB("🧪"), ECG("💓"), MRI("🧠"),
    XRAY("🫁"), CT("🔬"), EYE("👁️"), RX("💊"), OTHER("📋")
}

enum class RStatus(val label: String) {
    NORMAL("Normal"), CLEAR("Clear"), SAFE("Safe"),
    REVIEW("Review"), NOTE("Note"), WATCH("Watch"), FLAG("Flag")
}

/** Color applied to a metric value in the card */
enum class MColor { DEFAULT, RED, AMBER, GREEN }

data class Report(
    val id: String,
    val title: String,          // from AI: e.g. "Complete Blood Count (CBC)"
    val date: Date,             // from AI: OCR date or file timestamp
    val hospital: String,       // from AI: parsed from doc header
    val type: RType,            // from AI: auto-classified
    val status: RStatus,        // from AI: overall result
    val m1Val: String,          // metric 1 value  e.g. "14 values"
    val m1Lbl: String,          // metric 1 label  e.g. "Analyzed"
    val m1Color: MColor = MColor.DEFAULT,
    val m2Val: String,          // metric 2 value  e.g. "3 High"
    val m2Lbl: String,          // metric 2 label  e.g. "Flags"
    val m2Color: MColor = MColor.DEFAULT,
    val m3Val: String,          // metric 3 value  e.g. "98%"
    val m3Lbl: String = "Confidence",
    val m3Color: MColor = MColor.DEFAULT
)

/*
 Metric guide per report type:
 ┌─────────────┬──────────────────┬──────────────────┬────────────────┐
 │ Type        │ Metric 1         │ Metric 2         │ Metric 3       │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ LAB (CBC)   │ "14 values"      │ "3 High" 🔴      │ "98%"          │
 │             │ Analyzed         │ Flags            │ Confidence     │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ ECG         │ "72 bpm"         │ "Sinus" 🟢       │ "99%"          │
 │             │ Heart Rate       │ Rhythm           │ Confidence     │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ MRI         │ "No lesions"     │ "Normal" 🟢      │ "97%"          │
 │             │ Found            │ WM Signal        │ Confidence     │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ XRAY        │ "Mild"           │ "Monitor" 🟡     │ "96%"          │
 │             │ Opacity          │ Status           │ Confidence     │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ CT          │ "No nodules"     │ "Normal" 🟢      │ "98%"          │
 │             │ Detected         │ Airways          │ Confidence     │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ EYE         │ "Mild DR"        │ "Follow-up" 🟡   │ "94%"          │
 │             │ Grade 1          │ Needed           │ Confidence     │
 ├─────────────┼──────────────────┼──────────────────┼────────────────┤
 │ RX          │ "4 meds"         │ "No" 🟢          │ "99%"          │
 │             │ Identified       │ Interactions     │ Confidence     │
 └─────────────┴──────────────────┴──────────────────┴────────────────┘
*/