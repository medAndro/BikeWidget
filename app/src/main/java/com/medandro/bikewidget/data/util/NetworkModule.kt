package com.medandro.bikewidget.data.util

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.medandro.bikewidget.data.station.StationApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkModule {
    private val okHttpClient by lazy {
        OkHttpClient
            .Builder()
            .build()
    }

    val stationApiService: StationApiService by lazy {
        Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl("http://openapi.seoul.go.kr:8088")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(StationApiService::class.java)
    }
}
