package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteGalleryPhotoUseCaseTest {

    @MockK
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var deleteGalleryPhotoUseCase: DeleteGalleryPhotoUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        deleteGalleryPhotoUseCase = DeleteGalleryPhotoUseCase(galleryRepository)
    }

    @Test
    fun `given galleryRepository when invoke deleteGalleryPhoto then deletePhoto from database`() = runBlocking {
        //Given
        val mockPhoto = PhotoGallery("H1", "", "", "", "", "", "", "")
        coEvery { galleryRepository.deletePhoto(mockPhoto) } returns Unit
        //When
        deleteGalleryPhotoUseCase.invoke(mockPhoto)
        //Then
        coVerify(exactly = 1) { galleryRepository.deletePhoto(any()) }
    }
}