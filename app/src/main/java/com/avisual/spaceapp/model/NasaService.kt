package com.avisual.spaceapp.model

import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObject
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun marsRoverPhotosByEarthDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): RoverPhotosResult

    @GET("neo/rest/v1/feed")
    suspend fun searchNeoWsByDate(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): NearEarthObjectResult
}