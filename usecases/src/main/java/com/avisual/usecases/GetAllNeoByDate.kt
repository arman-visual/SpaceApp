package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class GetAllNeoByDate(private val neoRepository: NeoRepository) {

    suspend fun invoke(startDate: String): List<Neo>? = neoRepository.getAllNeoByDate(startDate)
}