package com.avisual.spaceapp.data.server

import com.avisual.data.source.NeoRemoteDataSource
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.spaceapp.data.toFrameworkNeo

class ServerNeoDataSource(private val nasaClient: NasaClient) : NeoRemoteDataSource {

    override suspend fun getAllNeoByDate(startDate: String, apiKey: String): List<Neo>? {
        return nasaClient.service
            .searchNeoWsByOnlyStartDate(startDate, apiKey)?.let {neoResult->
                neoResult.toFrameworkNeo()//<-- Mapping to NeoResult to -> FrameworkNeo
                    .map { it.toDomainNeo() }
            }
            //<-- Mapping to FramworkNeo to Domain
    }
}