package com.avisual.spaceapp.model

import retrofit2.Retrofit

object NasaClient{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity")
        .build()
    val service = retrofit.create(NasaService::class.java)
}