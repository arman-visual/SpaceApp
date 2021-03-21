package com.avisual.spaceapp.repository

import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import com.avisual.spaceapp.server.NasaClient

class PhotoCuriosityRepository {

    suspend fun findPhotosRoverFromServer(date: String, apiKey: String): RoverPhotosResult {
        return NasaClient.nasaService.marsRoverPhotosByEarthDate(date, apiKey)
    }

}