package com.avisual.usecases

import com.avisual.data.repository.RoverRepository
import com.avisual.domain.PhotoRover

class GetRoverPhotosByDate(private val roverRepository: RoverRepository) {

    suspend fun invoke(date: String): List<PhotoRover> {
        return roverRepository.getRoverPhotosByDate(date)
    }
}