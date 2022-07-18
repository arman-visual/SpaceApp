package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllStoredPhotosUseCaseTest {

    @MockK
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var getAllStoredPhotosUseCase: GetAllStoredPhotosUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getAllStoredPhotosUseCase = GetAllStoredPhotosUseCase(galleryRepository)
    }

    @Test
    fun `invoke getAllStoredPhotos and returns all photos stored in database`() = runBlocking {
        //Given
        val mockPhotos: List<PhotoGallery> = listOf(
            PhotoGallery("U01AEA1", "", "", "", "", "", "", ""),
            PhotoGallery("U01AEA2", "", "", "", "", "", "", "")
        )
        val mockFlow = flowOf(mockPhotos)
        coEvery { galleryRepository.getAllStoredPhotos() } returns mockFlow
        //Then
        val response = getAllStoredPhotosUseCase.invoke()
        //Assert
        assertEquals(mockFlow, response)
        coVerify(exactly = 1){ galleryRepository.getAllStoredPhotos() }
    }
}