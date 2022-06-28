package com.avisual.spaceapp.ui.gallery.showGallery.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel.GalleryUi
import com.avisual.usecases.GetGalleryPhotosByKeyword
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ShowGalleryViewModelTest {


    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword

    private lateinit var viewModel: ShowGalleryViewModel

    private val fakePhoto =
        PhotoGallery(
            nasa_id = "U1",
            jsonAllSized = "XX",
            url = "wwww.fake.com",
            date_created = "22-02-2022",
            description = "big",
            media_type = "Photo",
            photographer = "R. Williams",
            title = "Test Photo"

        )

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())//UnconfinedTestDispatcher()
        viewModel = ShowGalleryViewModel(getGalleryPhotosByKeyword)
    }

    @Test
    fun `observing LiveData when ViewModel is initialized returns default action to search photos from server`() =
        runTest {
            //Given
            val photos = listOf(fakePhoto.copy(nasa_id = "1"))
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(photos)
            //When

            //Then
            assertEquals(GalleryUi.Content(photos), viewModel.model.value)//Valor esperado - Valor obtenido
            assert(viewModel.model.value == GalleryUi.Content(photos))
        }

    @Test
    fun `observing LiveData when findByKeyword returns null`() =
        runTest {
            //Given
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(null)
            //When
            viewModel.findPhotosByKeyword("Nasa")
            //Then
            assertEquals(GalleryUi.Content(emptyList()), viewModel.model.value)//Valor esperado - Valor obtenido
        }

    @Test
    fun `observing LiveData when findByKeyword returns photos`() =
        runTest {
            //Given
            val photos = listOf(fakePhoto.copy(nasa_id = "1"))
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(photos)
            //When
            viewModel.findPhotosByKeyword("Nasa")
            //Then
            assertEquals(GalleryUi.Content(photos), viewModel.model.value)
        }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }
}