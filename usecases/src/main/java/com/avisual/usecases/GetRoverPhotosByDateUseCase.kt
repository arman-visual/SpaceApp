package com.avisual.usecases

import com.avisual.data.repository.RoverRepository
import com.avisual.domain.PhotoRover

class GetRoverPhotosByDateUseCase(private val roverRepository: RoverRepository) {

    suspend operator fun  invoke(date: String): List<PhotoRover> {
        return roverRepository.getRoverPhotosByDate(date)
    }
}