package com.avisual.data.repository

import com.avisual.data.source.NeoLocalDataSource
import com.avisual.data.source.NeoRemoteDataSource
import com.avisual.domain.Neo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NeoRepository(
    private val localDataSource: NeoLocalDataSource,
    private val remoteDataSource: NeoRemoteDataSource,
    private val apiKey: String
) {

    suspend fun getAllNeoByDate(startDate: String): List<Neo> {
        return remoteDataSource.getAllNeoByDate(startDate, apiKey)
    }

    fun getAllSavedNeo(): Flow<List<Neo>>? {
        return localDataSource.getAllStoredNeo()
    }

    suspend fun removeAsteroid(neo: Neo) =
        withContext(Dispatchers.IO) {
            localDataSource.removeNeo(neo)
        }

    suspend fun insertNeo(neo: Neo) =
        withContext(Dispatchers.IO) {
            localDataSource.insertNeo(neo)
        }

    suspend fun getNeoById(id: String) =
        withContext(Dispatchers.IO) {
            localDataSource.getNeoById(id)
        }
}
