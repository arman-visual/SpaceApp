package com.avisual.spaceapp.repository

import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import com.avisual.spaceapp.server.NasaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRoverRepository {

    suspend fun findPhotosRoverFromServer(date: String, apiKey: String): RoverPhotosResult =
        withContext(Dispatchers.IO) {
            NasaClient.nasaService.marsRoverPhotosByEarthDate(date, apiKey)
        }

}