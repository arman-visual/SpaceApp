package com.avisual.spaceapp.server

import com.avisual.data.source.NeoRemoteDataSource
import com.avisual.domain.Neo

class ServerNeoDataSource(private val nasaClient: NasaClient) : NeoRemoteDataSource {

    override suspend fun getAllNeoByDate(startDate: String, apiKey: String): List<Neo> {
        return nasaClient.service
            .searchNeoWsByOnlyStartDate(startDate, apiKey)
            .toFrameworkNeo()//<-- Mapping to NeoResult to -> FrameworkNeo
            .map { it.toDomainNeo() }//<-- Mapping to FramworkNeo to Domain
    }
}