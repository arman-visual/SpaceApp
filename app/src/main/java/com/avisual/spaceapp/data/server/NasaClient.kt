package com.avisual.spaceapp.data.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NasaClient(baseUrlApiNasa: String) {

    val clientSetup = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(this).build()
    }

    val service: NasaService = Retrofit.Builder()
        .baseUrl(baseUrlApiNasa)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientSetup)
        .build()
        .run { create(NasaService::class.java) }
    //val service: NasaService = retrofit.create(NasaService::class.java)
}