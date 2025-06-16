package com.medandro.bikewidget.data.station

import com.medandro.bikewidget.BuildConfig
import com.medandro.bikewidget.data.station.dto.SeoulBikeStation
import com.medandro.bikewidget.data.util.NetworkModule.stationApiService

class StationRemoteDataSource(
    private val apiService: StationApiService = stationApiService,
) {
    suspend fun getStations(
        startIndex: Int,
        endIndex: Int,
    ): Result<List<SeoulBikeStation>> =
        try {
            val apiKey = BuildConfig.seoulApiKey
            val response = apiService.getSeoulBikeStationList(apiKey, startIndex, endIndex)
            val stationList = response.rentBikeStatus?.stations ?: emptyList()
            Result.success(stationList)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
