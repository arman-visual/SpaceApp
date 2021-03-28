package com.avisual.spaceapp.model

data class Neo(
    var id: String,
    val name: String,
    val isPotentiallyHazardousAsteroid: Boolean,
    val absoluteMagnitudeH: Double,
    val nasaJplURL: String,
    val dayRegister: String
)
