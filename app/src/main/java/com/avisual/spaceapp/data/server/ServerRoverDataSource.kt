package com.avisual.spaceapp.data.server

import android.util.Log
import com.avisual.data.source.RoverRemoteDataSource
import com.avisual.domain.PhotoRover
import com.avisual.spaceapp.data.toDomainRover
import retrofit2.HttpException
import java.io.IOException

class ServerRoverDataSource(private val nasaClient: NasaClient) : RoverRemoteDataSource {

    override suspend fun getPhotosRoverByDate(date: String, apiKey: String): List<PhotoRover> {
        return try {
            val response = nasaClient.service.marsRoverPhotosByEarthDate(date, apiKey)
            response.body()?.photos?.map {
                it.toDomainRover()
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