package com.avisual.spaceapp.data.server

import com.avisual.data.source.RoverRemoteDataSource
import com.avisual.domain.PhotoRover
import com.avisual.spaceapp.data.toDomainRover

class ServerRoverDataSource : RoverRemoteDataSource {

    override suspend fun getPhotosRoverByDate(date: String, apiKey: String): List<PhotoRover> {
        return NasaClient.service.marsRoverPhotosByEarthDate(date, apiKey).photos.map {
            it.toDomainRover()
        }
    }
}