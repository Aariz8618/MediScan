package com.aariz.mediscan

/**
 * Shared singleton that holds the latest API result.
 * ProcessingFragment writes here → Result fragments read from here.
 */
object AnalysisResult {
    var patientReport: String = ""
    var doctorReport: String  = ""
    var reportType: String    = ""
    var modelUsed: String     = ""
    var confidence: Float     = 0f
    var patientName: String   = ""

    fun clear() {
        patientReport = ""
        doctorReport  = ""
        reportType    = ""
        modelUsed     = ""
        confidence    = 0f
        patientName   = ""
    }
}
