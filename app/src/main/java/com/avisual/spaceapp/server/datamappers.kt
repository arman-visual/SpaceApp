package com.avisual.spaceapp.server

import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.toFrameworkNeo
import com.avisual.domain.Neo as DomainNeo
import com.avisual.spaceapp.model.Neo as FrameworkNeo

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