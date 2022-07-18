package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetGalleryPhotosByKeywordUseCaseTest {

    @MockK
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var getGalleryPhotosByKeywordUseCase: GetGalleryPhotosByKeywordUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getGalleryPhotosByKeywordUseCase = GetGalleryPhotosByKeywordUseCase(galleryRepository)
    }

    @Test
    fun `given galleryRepository when call getGalleryPhotosByKeyword then returns photos`() = runBlocking {
        //Given
        val fakePhotos: List<PhotoGallery> = listOf(
            PhotoGallery("U01AEA1", "", "", "", "", "", "", "Moon"),
            PhotoGallery("U01AEA2", "", "", "", "", "", "", "Moon")
        )
        coEvery { galleryRepository.getGalleryPhotosByKeyword("Moon") } returns fakePhotos
        //When
        val result = getGalleryPhotosByKeywordUseCase.invoke("Moon")
        //Then
        assertEquals(fakePhotos, result)
        coVerify(exactly = 1) { galleryRepository.getGalleryPhotosByKeyword(any()) }
    }
}