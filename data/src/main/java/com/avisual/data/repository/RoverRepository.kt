package com.avisual.data.repository

import com.avisual.data.source.RoverRemoteDataSource
import com.avisual.domain.PhotoRover

class RoverRepository(
    private val roverRemoteDataSource: RoverRemoteDataSource,
    private val apiKey: String
) {

    suspend fun getRoverPhotosByDate(date: String): List<PhotoRover> {
        return roverRemoteDataSource.getPhotosRoverByDate(date, apiKey)
    }
}