package com.avisual.spaceapp.ui.gallery.showGallery.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel.*
import com.avisual.usecases.GetGalleryPhotosByKeyword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
    lateinit var observer: Observer<GalleryUi>

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
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ShowGalleryViewModel(getGalleryPhotosByKeyword)
    }

    @Test
    fun `observing LiveData when ViewModel is initialized`() = runTest {
        //Given
        val movies = listOf(fakePhoto.copy(nasa_id = "1"))
        whenever(getGalleryPhotosByKeyword.invoke("Nasa")).thenReturn(movies)
        //When
        viewModel.model.observeForever(observer)
        //Then
        verify(observer).onChanged(GalleryUi.Content(movies))
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

}