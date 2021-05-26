package com.avisual.spaceapp.data

import com.avisual.spaceapp.data.server.asteroidsNeoWsResponse.NearEarthObject as ServerNeo
import com.avisual.spaceapp.data.server.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.data.server.nasaRoverResponse.Photo
import com.avisual.domain.Neo as DomainNeo
import com.avisual.domain.PhotoGallery as GalleryDomain
import com.avisual.domain.PhotoRover as RoverDomain
import com.avisual.spaceapp.data.model.Neo as FrameworkNeo
import com.avisual.spaceapp.data.model.PhotoGallery as FrameworkGallery
import com.avisual.spaceapp.data.model.PhotoRover as FrameworkRover
import com.avisual.spaceapp.data.server.nasaLibraryResponse.Item as ServerModelGallery

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

fun ServerNeo.toFrameworkNeo(bindDay:String) = FrameworkNeo(
    this.id,
    this.name,
    this.isPotentiallyHazardousAsteroid,
    this.absoluteMagnitudeH,
    this.nasaJplURL,
    this.estimatedDiameter.kilometers.estimatedDiameterMin,
    this.estimatedDiameter.kilometers.estimatedDiameterMax,
    this.closeApproachData[0].relativeVelocity.kilometersPerSecond,
    this.closeApproachData[0].relativeVelocity.kilometersPerHour,
    this.closeApproachData[0].closeApproachDateFull,
    this.closeApproachData[0].closeApproachDate,
    this.closeApproachData[0].missDistance.kilometers,
    bindDay
)

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

fun ServerModelGallery.toGalleryDomain() = GalleryDomain(
    this.data_photo[0].nasa_id,
    this.href,
    this.links[0].href,
    this.data_photo[0].date_created,
    this.data_photo[0].description,
    this.data_photo[0].media_type,
    this.data_photo[0].photographer,
    this.data_photo[0].title
)

fun GalleryDomain.toGalleryFramework() = FrameworkGallery(
    nasa_id,
    jsonAllSized,
    url,
    date_created,
    description,
    media_type,
    photographer,
    title
)

fun FrameworkGallery.toGalleryDomain() = GalleryDomain(
    nasa_id,
    jsonAllSized,
    url,
    date_created,
    description,
    media_type,
    photographer,
    title
)