package com.avisual.spaceapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaClient {
    private val retrofitRover = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitLibrary = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val roverService: NasaRoverService = retrofitRover.create(NasaRoverService::class.java)

    val libraryService: NasaLibraryService = retrofitLibrary.create(NasaLibraryService::class.java)
}