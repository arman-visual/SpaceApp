package com.avisual.spaceapp.ui.gallery.showGallery.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ShowGalleryViewModelTest {


    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword

    @Mock
    private lateinit var observer: Observer<GalleryUi>

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
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ShowGalleryViewModel(getGalleryPhotosByKeyword)
    }

    @Test
    fun `observing LiveData when ViewModel is initialized returns default action to search photos from server`() =
        runTest {
            //Given
            val photos = listOf(fakePhoto.copy(nasa_id = "1"))
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(photos)
            viewModel.model.observeForever(observer)
            //When
            //Then
            assertEquals(GalleryUi.Content(photos), viewModel.model.value)
        }

    @Test
    fun `when viewModel is initialized it will be launched default search and return null`() =
        runTest {
            //Given
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(null)
            viewModel.model.observeForever(observer)
            //When
            //Then
            verify(observer).onChanged(GalleryUi.Content(emptyList()))
        }

    @Test
    fun `when viewModel is initialized then it will be launched default search and return photos by Keyword Nasa`() =
        runTest {
            //Given
            val photos = listOf(fakePhoto.copy(nasa_id = "1"))
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(photos)
            viewModel.model.observeForever(observer)
            //When
            //Then
            verify(observer).onChanged(GalleryUi.Content(photos))
        }

    @Test
    fun `when called findByKeyword then returns null`() =
        runTest {
            //Given
            whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(null)
            viewModel.model.observeForever(observer)
            //When
            viewModel.findPhotosByKeyword("Nasa")
            //Then
            assertEquals(GalleryUi.Content(emptyList()), viewModel.model.value)
        }

    @Test
    fun `when called findByKeyword then returns photos`() =
        runTest {
            //Given
            val photos = listOf(fakePhoto.copy(nasa_id = "1"))
            whenever(getGalleryPhotosByKeyword.invoke("Marth")).thenReturn(photos)
            viewModel.model.observeForever(observer)
            //When
            viewModel.findPhotosByKeyword("Marth")
            //Then
            assertEquals(GalleryUi.Content(photos), viewModel.model.value)
        }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }
}