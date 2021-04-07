package com.avisual.spaceapp.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NasaGalleryClient {

    private val clientSetup = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientSetup)
        .build()

    val service: NasaLibraryService = retrofit.create(NasaLibraryService::class.java)
}