package com.medandro.bikewidget.data.station.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SeoulBikeStation(
    // 대여소 ID
    @SerialName("stationId")
    val id: String,
    // 대여소 이름
    @SerialName("stationName")
    val name: String,
    // 위도
    @SerialName("stationLatitude")
    val latitude: Double,
    // 경도
    @SerialName("stationLongitude")
    val longitude: Double,
    // 자전거 주차 총 건수
    @SerialName("parkingBikeTotCnt")
    val bikeCount: String,
    // 거치대 개수
    @SerialName("rackTotCnt")
    val rackCount: Long,
    // 거치율
    @SerialName("shared")
    val sharedRate: String,
)
