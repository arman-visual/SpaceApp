package com.avisual.data.repository

import com.avisual.data.source.LocationDataSource

class RegionRepository(private val locationDataSource: LocationDataSource) {

    suspend fun getCurrentLocation(): String {
        return locationDataSource.getCurrentLocation()
    }
}