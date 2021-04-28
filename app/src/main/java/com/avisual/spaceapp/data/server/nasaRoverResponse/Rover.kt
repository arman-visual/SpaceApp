package com.avisual.spaceapp.model.nasaRoverResponse

data class Rover(
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val name: String,
    val status: String
)