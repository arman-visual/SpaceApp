package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class SaveNeoInDb(var neoRepository: NeoRepository) {

    suspend fun invoke(neo: Neo) {
        neoRepository.insertNeo(neo)
    }
}