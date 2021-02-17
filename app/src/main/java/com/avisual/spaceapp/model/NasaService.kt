package com.avisual.spaceapp.model

import com.avisual.spaceapp.model.roverPhotos.RoverPhotosResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("photos")
    suspend fun listPhotosByEarthDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): RoverPhotosResult
}