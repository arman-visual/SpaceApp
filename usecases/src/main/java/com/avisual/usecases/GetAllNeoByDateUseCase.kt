package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo

class GetAllNeoByDateUseCase(private val neoRepository: NeoRepository) {

    suspend operator fun  invoke(startDate: String): List<Neo> = neoRepository.getAllNeoByDate(startDate)
}