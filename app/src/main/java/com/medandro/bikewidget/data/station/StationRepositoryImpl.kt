package com.medandro.bikewidget.data.station

import android.util.Log
import com.medandro.bikewidget.data.station.dto.SeoulBikeStation
import com.medandro.bikewidget.data.util.Mapper.toDomain
import com.medandro.bikewidget.domain.Station
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class StationRepositoryImpl(
    private val remoteDataSource: StationRemoteDataSource,
) : StationRepository {
    override suspend fun getAllSeoulBikeStations(): Result<List<Station>> =
        coroutineScope {
            val ranges = listOf(1..999, 1000..1999, 2000..2999, 3000..3999)

            val deferredResults =
                ranges.map { range ->
                    async {
                        remoteDataSource.getStations(range.first, range.last)
                    }
                }
            val results = deferredResults.awaitAll()

            val allStations = mutableListOf<SeoulBikeStation>()
            for (result in results) {
                result
                    .onSuccess { stations ->
                        allStations.addAll(stations)
                    }.onFailure { exception ->
                        Log.e("StationRepository", "Failed to fetch a range: ${exception.message}")
                    }
            }
            Result.success(allStations.toDomain())
        }
}
