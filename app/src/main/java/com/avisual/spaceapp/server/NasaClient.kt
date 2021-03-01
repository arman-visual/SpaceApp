package com.avisual.spaceapp.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaClient {
    private val retrofitNasa = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitLibrary = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val nasaService: NasaService = retrofitNasa.create(NasaService::class.java)

    val libraryService: NasaLibraryService = retrofitLibrary.create(NasaLibraryService::class.java)
}