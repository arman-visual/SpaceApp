package com.avisual.data.source

import com.avisual.domain.Neo
import kotlinx.coroutines.flow.Flow

interface NeoLocalDataSource{
    fun getAllStoredNeo(): Flow<List<Neo>>
}