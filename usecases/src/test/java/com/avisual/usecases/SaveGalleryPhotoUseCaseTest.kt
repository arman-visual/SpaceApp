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

class SaveGalleryPhotoUseCaseTest {

    @MockK
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var saveGalleryPhotoUseCase: SaveGalleryPhotoUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        saveGalleryPhotoUseCase = SaveGalleryPhotoUseCase(galleryRepository)
    }

    @Test
    fun `given galleryRepository when invoke saveGalleryPhoto then save GalleryPhoto in DB`() =
        runBlocking {
            //Given
            val fakePhoto = PhotoGallery("U01AEA1", "", "", "", "", "", "", "")
            coEvery { galleryRepository.savePhoto(fakePhoto) } returns Unit
            //When
            saveGalleryPhotoUseCase.invoke(fakePhoto)
            //Then
            coVerify { galleryRepository.savePhoto(any()) }
        }
}