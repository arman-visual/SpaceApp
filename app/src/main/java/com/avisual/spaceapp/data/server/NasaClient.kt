package com.avisual.spaceapp.data.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NasaClient(baseUrlApiNasa:String) {

    private val clientSetup = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

     val service: NasaService = Retrofit.Builder()
        .baseUrl(baseUrlApiNasa)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientSetup)
        .build()
        .run { create(NasaService::class.java) }
    //val service: NasaService = retrofit.create(NasaService::class.java)
}