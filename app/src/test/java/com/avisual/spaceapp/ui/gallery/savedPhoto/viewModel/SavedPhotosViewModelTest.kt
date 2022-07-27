package com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel.SavedPhotosViewModel.SavedPhotosUi
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetAllStoredPhotos
import com.avisual.usecases.SaveGalleryPhoto
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class SavedPhotosViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var saveGalleryPhoto: SaveGalleryPhoto

    @Mock
    private lateinit var deleteGalleryPhoto: DeleteGalleryPhoto

    @Mock
    private lateinit var getAllStoredPhotos: GetAllStoredPhotos

    @Mock
    private lateinit var observer: Observer<SavedPhotosUi>

    private lateinit var viewModel: SavedPhotosViewModel

    private val mockPhoto =
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
        viewModel = SavedPhotosViewModel(saveGalleryPhoto, deleteGalleryPhoto, getAllStoredPhotos)
    }

    @Test
    fun `Observing livedata when viewModel is created and returns stored photos`() =
        runTest {
            //GIVEN
            val photos = listOf(mockPhoto.copy(nasa_id = "TST1"))
            whenever(getAllStoredPhotos.invoke()).thenReturn(flowOf(photos))
            viewModel.modelSavedPhotos.observeForever(observer)
            //WHEN
            viewModel.getPhotosFromDb()
            //THEN
            verify(observer).onChanged(SavedPhotosUi.Content(photos.map { it.toGalleryFramework() }))
            assertEquals(SavedPhotosUi.Content(photos.map { it.toGalleryFramework() }), viewModel.modelSavedPhotos.value)
        }

    @Test
    fun `Observing livedata when viewModel is created then return null`() =
        runTest {
            //GIVEN
            whenever(getAllStoredPhotos.invoke()).thenReturn(null)
            viewModel.modelSavedPhotos.observeForever(observer)
            //WHEN
            viewModel.getPhotosFromDb()
            //THEN
            assertEquals(null, viewModel.modelSavedPhotos.value)
        }

    @Test
    fun `Observing livedata when saved photo to database`() =
        runTest {
            //GIVEN
            val photo = mockPhoto.copy(nasa_id = "1TST")
            whenever(saveGalleryPhoto.invoke(photo)).thenReturn(Unit)
            viewModel.modelSavedPhotos.observeForever(observer)
            //WHEN
            viewModel.savePhoto(photo.toGalleryFramework())
            //THEN
            verify(saveGalleryPhoto).invoke(photo)
        }

    @Test
    fun `Observing livedata when invoke delete photo from database`() =
        runTest {
            //GIVEN
            val photo = mockPhoto.copy(nasa_id = "1TST")
            whenever(deleteGalleryPhoto.invoke(photo)).thenReturn(Unit)
            viewModel.modelSavedPhotos.observeForever(observer)
            //WHEN
            viewModel.deletePhoto(photo.toGalleryFramework())
            //THEN
            verify(deleteGalleryPhoto).invoke(photo)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}