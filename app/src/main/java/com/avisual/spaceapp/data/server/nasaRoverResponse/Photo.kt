package com.avisual.spaceapp.data.server.nasaRoverResponse

data class Photo(
    val camera: Camera,
    val earth_date: String = "",
    val id: Int = -1,
    val img_src: String = "",
    val rover: Rover,
    val sol: Int = -1
)