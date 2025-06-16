package com.medandro.bikewidget.data.station

import com.medandro.bikewidget.data.station.dto.SeoulBikeStationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StationApiService {
    @GET("{authKey}/json/bikeList/{startIndex}/{endIndex}/")
    suspend fun getSeoulBikeStationList(
        @Path("authKey") authKey: String,
        @Path("startIndex") startIndex: Int,
        @Path("endIndex") endIndex: Int,
    ): SeoulBikeStationResponse
}
