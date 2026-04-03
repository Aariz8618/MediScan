package com.aariz.mediscan

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MediScanApi {

    @Multipart
    @POST("analyze/report")
    suspend fun analyzeReport(
        @Part file: MultipartBody.Part,
        @Part("patient_name") patientName: RequestBody,
        @Part("patient_age") patientAge: RequestBody,
        @Part("patient_gender") patientGender: RequestBody
    ): Response<AnalysisResponse>

    @Multipart
    @POST("analyze/image")
    suspend fun analyzeImage(
        @Part file: MultipartBody.Part,
        @Part("image_type") imageType: RequestBody,
        @Part("patient_name") patientName: RequestBody,
        @Part("patient_age") patientAge: RequestBody,
        @Part("patient_gender") patientGender: RequestBody
    ): Response<AnalysisResponse>

    @Multipart
    @POST("analyze/prescription")
    suspend fun analyzePrescription(
        @Part file: MultipartBody.Part,
        @Part("patient_name") patientName: RequestBody,
        @Part("patient_age") patientAge: RequestBody,
        @Part("patient_gender") patientGender: RequestBody
    ): Response<AnalysisResponse>
}
