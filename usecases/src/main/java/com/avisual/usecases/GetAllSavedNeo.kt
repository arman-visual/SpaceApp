package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo
import kotlinx.coroutines.flow.Flow

class GetAllSavedNeo(private val neoRepository: NeoRepository) {

    fun invoke(): Flow<List<Neo>> = neoRepository.getAllSavedNeo()
}