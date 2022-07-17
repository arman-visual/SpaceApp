package com.avisual.usecases

import com.avisual.data.repository.RoverRepository
import com.avisual.domain.PhotoRover
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRoverPhotosByDateTest {

    @MockK
    private lateinit var roverRepository: RoverRepository

    private lateinit var getRoverPhotosByDate: GetRoverPhotosByDate

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRoverPhotosByDate = GetRoverPhotosByDate(roverRepository)
    }

    @Test
    fun `given roverRepository when invoke getRoverPhotosByDate then returns roverPhotos`() =
        runBlocking {
            //Given
            val fakeRoverPhotos = listOf(PhotoRover(1, "", "", "", 1, "", "", "", "", "", 2))
            coEvery { roverRepository.getRoverPhotosByDate("22-02-2016") } returns fakeRoverPhotos
            //When
            val result = getRoverPhotosByDate.invoke("22-02-2016")
            //Then
            assertEquals(fakeRoverPhotos, result)
            coVerify { roverRepository.getRoverPhotosByDate(any()) }
        }
}