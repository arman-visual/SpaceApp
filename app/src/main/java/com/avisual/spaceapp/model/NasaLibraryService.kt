package com.avisual.spaceapp.model

import com.avisual.spaceapp.model.nasaLibraryResponse.CollectionNasaResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaLibraryService {
    @GET("search")
    suspend fun searchContain(
        @Query("q") keyword: String
    ): CollectionNasaResult
}