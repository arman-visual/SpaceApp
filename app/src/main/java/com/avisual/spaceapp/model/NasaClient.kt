package com.avisual.spaceapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaClient {
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val service: NasaService = retrofit.create(NasaService::class.java)
}