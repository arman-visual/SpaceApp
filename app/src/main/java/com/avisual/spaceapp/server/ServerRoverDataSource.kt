package com.avisual.spaceapp.server

import com.avisual.data.source.RoverRemoteDataSource
import com.avisual.domain.PhotoRover

class ServerRoverDataSource : RoverRemoteDataSource {

    override suspend fun getPhotosRoverByDate(date: String, apiKey: String): List<PhotoRover> {
        return NasaClient.service.marsRoverPhotosByEarthDate(date, apiKey).photos.map {
            it.toDomainRover()
        }
    }
}