package com.avisual.data.source

import com.avisual.domain.Neo
import kotlinx.coroutines.flow.Flow

interface NeoLocalDataSource {
    fun getAllStoredNeo(): Flow<List<Neo>>
    suspend fun removeNeo(neo: Neo)
    suspend fun insertNeo(neo: Neo)
    suspend fun getNeoById(id: String): Neo?
}