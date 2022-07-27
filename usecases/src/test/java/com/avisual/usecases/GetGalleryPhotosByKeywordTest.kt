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

class GetGalleryPhotosByKeywordTest {

    @MockK
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getGalleryPhotosByKeyword = GetGalleryPhotosByKeyword(galleryRepository)
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
        val result = getGalleryPhotosByKeyword.invoke("Moon")
        //Then
        assertEquals(fakePhotos, result)
        coVerify(exactly = 1) { galleryRepository.getGalleryPhotosByKeyword(any()) }
    }
}