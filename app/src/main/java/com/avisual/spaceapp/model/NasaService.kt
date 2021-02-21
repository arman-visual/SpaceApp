package com.avisual.spaceapp.model

import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun marsRoverPhotosByEarthDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): RoverPhotosResult

    @GET
    suspend fun neo(){
        TODO()
    }
}