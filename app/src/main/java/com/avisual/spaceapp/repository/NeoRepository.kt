package com.avisual.spaceapp.repository

import androidx.lifecycle.LiveData
import com.avisual.spaceapp.database.AsteroidDao
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.server.NasaClient
import com.avisual.spaceapp.server.toFrameworkNeo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NeoRepository(var database: Db, private val apiKey: String) {

    private var asteroidDao: AsteroidDao = database.asteroidDao()

    suspend fun findNeoByRangeDate(
        dateInit: String,
        dateFinish: String
    ): NearEarthObjectResult =
        withContext(Dispatchers.IO) {
            NasaClient.service.searchNeoWsByDate(dateInit, dateFinish, apiKey)
        }

    suspend fun findNeoByOnlyStartDate(
        startDate: String,
    ): NearEarthObjectResult =
        withContext(Dispatchers.IO) {
            NasaClient.service.searchNeoWsByOnlyStartDate(startDate, apiKey)
        }

    suspend fun removeAsteroid(asteroid: Neo) =
        withContext(Dispatchers.IO) {
            asteroidDao.delete(asteroid)
        }

    suspend fun insertAsteroid(asteroid: Neo) =
        withContext(Dispatchers.IO) {
            asteroidDao.insert(asteroid)
        }

    suspend fun getAsteroidById(id: String): Neo? = withContext(Dispatchers.IO) {
        asteroidDao.getById(id)
    }

    fun getAllAsteroidsSaved(): LiveData<List<Neo>> {
        return asteroidDao.getAllLiveData()
    }
}