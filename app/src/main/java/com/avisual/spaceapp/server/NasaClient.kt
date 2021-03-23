package com.avisual.spaceapp.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NasaClient {

    private val clientSetup = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofitNasa = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientSetup)
        .build()

    private val retrofitLibrary = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val nasaService: NasaService = retrofitNasa.create(NasaService::class.java)

    val libraryService: NasaLibraryService = retrofitLibrary.create(NasaLibraryService::class.java)
}