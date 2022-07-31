package com.avisual.spaceapp.data.server

import com.avisual.spaceapp.data.server.nasaLibraryResponse.CollectionNasaResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaLibraryService {
    companion object{
        private const val MEDIA_TYPE_IMAGE = "image"
    }
    @GET("search")
    suspend fun searchContain(
        @Query("q") keyword: String,
        @Query("media_type") mediaType:String = MEDIA_TYPE_IMAGE
    ): Response<CollectionNasaResult>
}