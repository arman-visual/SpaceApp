package com.avisual.spaceapp.data.server

import com.avisual.data.source.NeoRemoteDataSource
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.spaceapp.data.toFrameworkNeo

class ServerNeoDataSource : NeoRemoteDataSource {

    override suspend fun getAllNeoByDate(startDate: String, apiKey: String): List<Neo> {
        return NasaClient.service
            .searchNeoWsByOnlyStartDate(startDate, apiKey)
            .toFrameworkNeo()//<-- Mapping to NeoResult to -> FrameworkNeo
            .map { it.toDomainNeo() }//<-- Mapping to FramworkNeo to Domain
    }
}