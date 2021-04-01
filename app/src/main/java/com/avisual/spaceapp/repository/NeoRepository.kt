package com.avisual.spaceapp.repository

import androidx.lifecycle.LiveData
import com.avisual.spaceapp.database.AsteroidDao
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.server.NasaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NeoRepository(var database: Db) {

    private var asteroidDao: AsteroidDao = database.asteroidDao()

    suspend fun findNeoByRangeDate(
        dateInit: String,
        dateFinish: String,
        apiKey: String
    ): NearEarthObjectResult =
        withContext(Dispatchers.IO) {
            NasaClient.nasaService.searchNeoWsByDate(dateInit, dateFinish, apiKey)
        }

    suspend fun findNeoByOnlyStartDate(
        startDate: String,
        apiKey: String
    ): NearEarthObjectResult =
        withContext(Dispatchers.IO) {
            NasaClient.nasaService.searchNeoWsByOnlyStartDate(startDate, apiKey)
        }

    suspend fun removeAsteroid(asteroid: Neo) =
        withContext(Dispatchers.IO) {
            asteroidDao.delete(asteroid)
        }

    fun getAllAsteroidsSaved(): LiveData<List<Neo>> {
        return asteroidDao.getAllLiveData()
    }
}