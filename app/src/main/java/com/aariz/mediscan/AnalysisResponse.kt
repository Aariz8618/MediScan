package com.aariz.mediscan

import com.google.gson.annotations.SerializedName

data class AnalysisResponse(
    @SerializedName("success")        val success: Boolean,
    @SerializedName("report_type")    val reportType: String,
    @SerializedName("patient_report") val patientReport: String,
    @SerializedName("doctor_report")  val doctorReport: String,
    @SerializedName("patient_name")   val patientName: String,
    @SerializedName("model_used")     val modelUsed: String?,
    @SerializedName("confidence")     val confidence: Float?,
    @SerializedName("error")          val error: String?
)
