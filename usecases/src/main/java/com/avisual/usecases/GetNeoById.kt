package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class GetNeoById(var neoRepository: NeoRepository) {

    suspend fun invoke(id: String): Neo? {
        return neoRepository.getNeoById(id)
    }
}