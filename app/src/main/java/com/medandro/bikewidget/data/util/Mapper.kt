package com.medandro.bikewidget.data.util

import com.medandro.bikewidget.data.station.dto.SeoulBikeStation
import com.medandro.bikewidget.domain.City
import com.medandro.bikewidget.domain.Station

object Mapper {
    fun List<SeoulBikeStation>.toDomain(): List<Station> =
        this.mapIndexed { index, station ->
            station.toDomain(index)
        }

    fun SeoulBikeStation.toDomain(index: Int = -1): Station {
        val regex = Regex("^(\\d+)\\.?\\s*(.*)")
        val matchResult = regex.find(this.name)

        return if (matchResult != null) {
            val (visibleIndex, stationName) = matchResult.destructured
            Station(
                visibleIndex = visibleIndex,
                index = index,
                id = this.id,
                name = stationName,
                latitude = this.latitude,
                longitude = this.longitude,
                parkingQuantity = this.bikeCount.toIntOrNull() ?: 0,
                city = City.Seoul,
            )
        } else {
            Station(
                visibleIndex = "",
                index = index,
                id = this.id,
                name = this.name.trim(),
                latitude = this.latitude,
                longitude = this.longitude,
                parkingQuantity = this.bikeCount.toIntOrNull() ?: 0,
                city = City.Seoul,
            )
        }
    }
}
