package com.avisual.usecases

import com.avisual.data.repository.RegionRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCurrentRegionUseCaseTest {

    @MockK
    private lateinit var regionRepository: RegionRepository

    private lateinit var getCurrentRegionUseCase: GetCurrentRegionUseCase

    private val mockRegion = "ES"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCurrentRegionUseCase = GetCurrentRegionUseCase(regionRepository)
    }

    @Test
    fun `invoke getCurrentLocation then return current region`() = runBlocking {
        //GIVEN
        coEvery { regionRepository.getCurrentLocation() }.returns(mockRegion)
        //WHEN
        val currentRegion = getCurrentRegionUseCase()
        //THEN
        assert(mockRegion == currentRegion)
    }
}