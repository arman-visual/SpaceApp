package com.avisual.data.source

import com.avisual.domain.Neo

interface NeoRemoteDataSource {
    suspend fun getAllNeoByDate(startDate:String, apiKey:String):List<Neo>?
}