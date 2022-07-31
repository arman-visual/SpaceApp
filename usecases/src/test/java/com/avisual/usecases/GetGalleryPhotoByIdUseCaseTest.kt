package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetGalleryPhotoByIdUseCaseTest {

    @MockK
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var getGalleryPhotoByIdUseCase: GetGalleryPhotoByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getGalleryPhotoByIdUseCase = GetGalleryPhotoByIdUseCase(galleryRepository)
    }

    @Test
    fun `invoke getPhotoById and returns photo`() = runBlocking {
        //Given
        val mockPhoto = PhotoGallery("U01AEA1", "", "", "", "", "", "", "")
        coEvery { galleryRepository.getPhotoById("U01A1EA1") } returns mockPhoto
        //When
        val result = getGalleryPhotoByIdUseCase.invoke("U01A1EA1")
        //Then
        assertEquals(mockPhoto, result)
    }
}