package com.aariz.mediscan.model

data class Report(
    val id: Int,
    val name: String,
    val date: String,
    val hospitalName: String,
    val status: String,
    val iconResId: Int
)
