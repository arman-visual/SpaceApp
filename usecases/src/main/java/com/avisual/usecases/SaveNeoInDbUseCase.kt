package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class SaveNeoInDbUseCase(var neoRepository: NeoRepository) {

    suspend operator fun  invoke(neo: Neo) {
        neoRepository.insertNeo(neo)
    }
}