package com.medandro.bikewidget.domain

data class Station(
    val rawIndex: String,
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val parkingQuantity: Int,
    val city: City,
)
