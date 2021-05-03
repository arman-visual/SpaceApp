package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class RemoveNeo(private val neoRepository: NeoRepository) {

    suspend fun invoke(neo: Neo) = neoRepository.removeAsteroid(neo)
}