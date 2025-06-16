package com.medandro.bikewidget.domain

sealed class City(
    val regionName: String,
) {
    data object Seoul : City(
        regionName = "서울",
    )
}
