package com.avisual.domain

data class PhotoRover(
    val id: Int,
    val img_src: String,
    val full_name: String,
    val camera_name: String,
    val rover_id: Int,
    val earth_date: String,
    val landing_date: String,
    val launch_date: String,
    val rover_name: String,
    val status: String,
    val sol: Int
)