package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoveNeoUseCaseTest {

    @MockK
    private lateinit var neoRepository: NeoRepository

    private lateinit var removeNeoUseCase: RemoveNeoUseCase

    @Before
    fun onfBefore() {
        MockKAnnotations.init(this)
        removeNeoUseCase = RemoveNeoUseCase(neoRepository)
    }

    @Test
    fun `given neoRepository when invoke usecase removeNeo then remove Neo from database`() =
        runBlocking {
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
            coEvery { neoRepository.removeAsteroid(fakeNeo) }.returns(Unit)
            //When
            removeNeoUseCase.invoke(fakeNeo)
            //Then
            coVerify(exactly = 1) { neoRepository.removeAsteroid(any()) }
        }
}