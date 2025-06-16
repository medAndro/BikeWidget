package com.medandro.bikewidget.data.station.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SeoulBikeStationResponse(
    @SerialName("rentBikeStatus")
    val rentBikeStatus: RentBikeStatus?,
)
