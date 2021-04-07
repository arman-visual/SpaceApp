package com.avisual.spaceapp.repository

import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import com.avisual.spaceapp.server.NasaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRoverRepository(private val apiKey: String) {

    suspend fun findPhotosRoverFromServer(date: String): RoverPhotosResult =
        withContext(Dispatchers.IO) {
            NasaClient.service.marsRoverPhotosByEarthDate(date, apiKey)
        }

}