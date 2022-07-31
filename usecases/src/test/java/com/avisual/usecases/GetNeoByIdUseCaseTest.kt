package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNeoByIdUseCaseTest {

    @MockK
    private lateinit var neoRepository: NeoRepository

    private lateinit var getNeoByIdUseCase: GetNeoByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getNeoByIdUseCase = GetNeoByIdUseCase(neoRepository)
    }

    @Test
    fun `given neoRepository when invoke getNeoById then returns Neo`() = runBlocking {
        //Given
        val fakeNeo = Neo(
            "1",
            "MockNeo",
            true,
            2.0,
            "",
            3.0,
            5.0,
            "300 km/s",
            "30000km/h",
            "19-20-2020",
            "100000",
            "1.0000.000km",
            "23-02-2022"
        )
        coEvery { neoRepository.getNeoById("1") } returns fakeNeo
        //When
        var result = getNeoByIdUseCase.invoke("1")
        //Then
        assertEquals(fakeNeo, result)
        coVerify(exactly = 1) { neoRepository.getNeoById(any()) }
    }
}