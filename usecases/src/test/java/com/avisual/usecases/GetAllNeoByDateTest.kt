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

class GetAllNeoByDateTest {

    @MockK
    private lateinit var neoRepository: NeoRepository

    private lateinit var getAllNeoByDate: GetAllNeoByDate

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getAllNeoByDate = GetAllNeoByDate(neoRepository)
    }

    @Test
    fun `invoke getAllNeoByDate and returns Neos near of date `() = runBlocking {
        //Given
        val mockNeos = listOf(
            Neo(
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
            ),
            Neo(
                "2",
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
        )
        coEvery { neoRepository.getAllNeoByDate("22-02-2022") } returns mockNeos
        //When
        val response = getAllNeoByDate.invoke("22-02-2022")
        //Then
        coVerify(exactly = 1) { neoRepository.getAllNeoByDate(any()) }
        assertEquals(mockNeos, response)
    }
}