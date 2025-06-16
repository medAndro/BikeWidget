package com.medandro.bikewidget.data.station

import com.medandro.bikewidget.domain.Station

interface StationRepository {
    suspend fun getAllSeoulBikeStations(): Result<List<Station>>
}
