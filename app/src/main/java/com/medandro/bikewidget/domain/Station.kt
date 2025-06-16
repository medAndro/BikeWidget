package com.medandro.bikewidget.domain

data class Station(
    val visibleIndex: String,
    val index: Int,
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val parkingQuantity: Int,
    val city: City,
)
