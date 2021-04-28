package com.avisual.spaceapp.model.nasaRoverResponse

import com.avisual.spaceapp.model.PhotoRover

data class Photo(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)

fun Photo.convertToPhotoRover() = PhotoRover(
    this.id,
    this.img_src,
    this.camera.full_name,
    this.camera.name,
    this.rover.id,
    this.earth_date,
    this.rover.landing_date,
    this.rover.launch_date,
    this.rover.name,
    this.rover.status,
    this.sol
)