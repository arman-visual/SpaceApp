package com.avisual.data.repository

import com.avisual.domain.Neo

class NeoRepository(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource,
    val apiKey: String
) {

     fun findNeoByOnlyStartDate(): List<Neo> {
        return remoteDataSource.findNeoByRangeDate()
    }
}


interface LocalDataSource

interface RemoteDataSource {
    fun findNeoByRangeDate():List<Neo>
}