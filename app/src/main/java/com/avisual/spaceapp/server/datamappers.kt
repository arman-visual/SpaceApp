package com.avisual.spaceapp.server

import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.toFrameworkNeo
import com.avisual.spaceapp.model.nasaRoverResponse.Photo
import com.avisual.domain.Neo as DomainNeo
import com.avisual.domain.PhotoRover as RoverDomain
import com.avisual.spaceapp.model.Neo as FrameworkNeo
import com.avisual.spaceapp.model.PhotoRover as FrameworkRover

fun NearEarthObjectResult.toFrameworkNeo(): List<FrameworkNeo> {

    val frameworkNeos: MutableList<FrameworkNeo> = mutableListOf()
    val totalDays = registerDay.size
    val keys = registerDay.keys.sorted()

    for (i in 0 until totalDays) {
        registerDay.getValue(keys[i])
            .map { neo -> frameworkNeos.add(neo.toFrameworkNeo(keys[i])) }
    }
    return frameworkNeos
}

fun FrameworkNeo.toDomainNeo(): DomainNeo = DomainNeo(
    id,
    name,
    isPotentiallyHazardousAsteroid,
    absoluteMagnitudeH,
    nasaJplURL,
    minDiameter,
    maxDiameter,
    relativeVelocitySeconds,
    relativeVelocityHour,
    approachDate,
    approachDateFull,
    missDistance,
    dayRegister
)

fun DomainNeo.toFrameworkNeo(): FrameworkNeo = FrameworkNeo(
    id,
    name,
    isPotentiallyHazardousAsteroid,
    absoluteMagnitudeH,
    nasaJplURL,
    minDiameter,
    maxDiameter,
    relativeVelocitySeconds,
    relativeVelocityHour,
    approachDate,
    approachDateFull,
    missDistance,
    dayRegister
)

fun Photo.toDomainRover() = RoverDomain(
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

fun RoverDomain.toFrameworkRover() = FrameworkRover(
    id,
    img_src,
    full_name,
    camera_name,
    rover_id,
    earth_date,
    landing_date,
    launch_date,
    rover_name,
    status,
    sol
)