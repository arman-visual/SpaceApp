package com.avisual.spaceapp.data.server

import android.util.Log
import com.avisual.data.source.NeoRemoteDataSource
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.spaceapp.data.toFrameworkNeo
import retrofit2.HttpException
import java.io.IOException

class ServerNeoDataSource(private val nasaClient: NasaClient) : NeoRemoteDataSource {

    override suspend fun getAllNeoByDate(startDate: String, apiKey: String): List<Neo> {
        return try {
            val response = nasaClient.service
                .searchNeoWsByOnlyStartDate(startDate, apiKey)
            response.body()?.let { neoResult ->
                neoResult.toFrameworkNeo()//<-- Mapping to NeoResult to -> FrameworkNeo
                    .map { it.toDomainNeo() }
            } ?: emptyList()
        } catch (exception: IOException) {
            Log.e("ServerRoverDataSource", "IOException, check internet connection")
            emptyList()
        } catch (exception: HttpException) {
            Log.e("ServerRoverDataSource", exception.message())
            emptyList()
        }
    }
}