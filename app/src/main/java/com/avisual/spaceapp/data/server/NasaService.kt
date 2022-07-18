package com.avisual.spaceapp.data.server

import com.avisual.spaceapp.data.server.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.data.server.nasaRoverResponse.RoverPhotosResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun marsRoverPhotosByEarthDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): Response<RoverPhotosResult>

    @GET("neo/rest/v1/feed")
    suspend fun searchNeoWsByOnlyStartDate(
        @Query("start_date") startDate: String,
        @Query("api_key") apiKey: String
    ): Response<NearEarthObjectResult>
}