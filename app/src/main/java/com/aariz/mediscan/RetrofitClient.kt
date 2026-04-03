package com.aariz.mediscan

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Change this to your PC's local IP when testing on a real device
    // Use 10.0.2.2 for Android Emulator (maps to localhost)
    // Use your PC IP like 192.168.1.x for a real phone on same WiFi
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)   // Claude API can take time
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val api: MediScanApi by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MediScanApi::class.java)
    }
}
