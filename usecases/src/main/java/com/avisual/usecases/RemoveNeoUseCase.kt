package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class RemoveNeoUseCase(private val neoRepository: NeoRepository) {

    suspend operator fun  invoke(neo: Neo) = neoRepository.removeAsteroid(neo)
}