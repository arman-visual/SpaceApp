package com.avisual.data.source

import com.avisual.domain.Neo
import kotlinx.coroutines.flow.Flow

interface NeoLocalDataSource{
    fun getAllStoredNeo(): Flow<List<Neo>>
    suspend fun removeAsteroid(asteroid:Neo)
    suspend fun insertAsteroid(asteroid: Neo)
    suspend fun getAsteroidById(id:String):Neo
}