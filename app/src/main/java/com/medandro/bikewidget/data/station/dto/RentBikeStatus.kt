package com.medandro.bikewidget.data.station.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class RentBikeStatus(
    @SerialName("list_total_count")
    val totalCount: Int,
    @SerialName("row")
    val stations: List<SeoulBikeStation>,
)
