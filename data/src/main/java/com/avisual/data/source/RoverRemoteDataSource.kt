package com.avisual.data.source

import com.avisual.domain.PhotoRover

interface RoverRemoteDataSource {

    suspend fun getPhotosRoverByDate(date: String, apiKey: String): List<PhotoRover>
}