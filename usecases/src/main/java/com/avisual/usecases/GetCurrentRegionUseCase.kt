package com.avisual.usecases

import com.avisual.data.repository.RegionRepository

class GetCurrentRegionUseCase(private val regionRepository: RegionRepository) {

    suspend operator fun invoke(): String {
        return regionRepository.getCurrentLocation()
    }

}