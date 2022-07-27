package com.avisual.usecases

import com.avisual.data.repository.NeoRepository
import com.avisual.domain.Neo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetStoredNeosTest {

    @MockK
    private lateinit var neoRepository: NeoRepository

    private lateinit var getStoredNeos: GetStoredNeos

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStoredNeos = GetStoredNeos(neoRepository)
    }

    @Test
    fun `given when then`() = runBlocking {
        //Given
        val fakeNeos = listOf(
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
            )
        )
        val fakeFlowResponse = flowOf(fakeNeos)
        coEvery { neoRepository.getAllSavedNeo() } returns fakeFlowResponse
        //When
        val response = getStoredNeos.invoke()
        //Then
        assertEquals(fakeFlowResponse, response)
        coVerify(exactly = 1) { neoRepository.getAllSavedNeo() }
    }

}