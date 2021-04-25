package com.avisual.data.repository

import com.avisual.data.source.NeoLocalDataSource
import com.avisual.data.source.NeoRemoteDataSource
import com.avisual.domain.Neo
import kotlinx.coroutines.flow.Flow

class NeoRepository(
    private val localDataSource: NeoLocalDataSource,
    private val remoteDataSource: NeoRemoteDataSource,
    private val apiKey: String
) {

    suspend fun getAllNeoByDate(startDate: String): List<Neo> {
        return remoteDataSource.getAllNeoByDate(startDate, apiKey)
    }

    fun getAllSavedNeo(startDate: String): Flow<List<Neo>> {
        return localDataSource.getAllStoredNeo()
    }
}
