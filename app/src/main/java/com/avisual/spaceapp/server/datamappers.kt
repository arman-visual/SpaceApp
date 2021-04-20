package com.avisual.spaceapp.server

import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.toNeo

fun convertToOneListDate(
    response: NearEarthObjectResult,
    totalAsteroids: MutableList<Neo>
): List<Neo> {
    val totalDays = response.registerDay.size
    val keys = response.registerDay.keys.sorted()

    for (i in 0 until totalDays) {
        response.registerDay.getValue(keys[i])
            .map { neo -> totalAsteroids.add(neo.toNeo(keys[i])) }
    }
    return totalAsteroids
}