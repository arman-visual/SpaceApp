package com.avisual.spaceapp.model

import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaRoverService {
    @GET("photos")
    suspend fun listPhotosByEarthDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): RoverPhotosResult
}