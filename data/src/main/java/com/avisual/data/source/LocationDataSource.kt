package com.avisual.data.source

interface LocationDataSource {
    suspend fun getCurrentLocation(): String
}