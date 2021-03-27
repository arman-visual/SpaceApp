package com.avisual.spaceapp.repository

import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.server.NasaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NeoRepository {

    suspend fun findPhotosRoverFromServer(
        dateInit: String,
        dateFinish: String,
        apiKey:String
    ): NearEarthObjectResult =
        withContext(Dispatchers.IO) {
            NasaClient.nasaService.searchNeoWsByDate(dateInit, dateFinish, apiKey)
        }
}