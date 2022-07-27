package com.avisual.spaceapp.ui.gallery.detailPhoto.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetGalleryPhotoById
import com.avisual.usecases.SaveGalleryPhoto
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
class DetailPhotoViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var saveGalleryPhoto: SaveGalleryPhoto

    @Mock
    lateinit var deleteGalleryPhoto: DeleteGalleryPhoto

    @Mock
    lateinit var getGalleryPhotoById: GetGalleryPhotoById

    @Mock
    private lateinit var observer: Observer<Boolean>

    private lateinit var viewModel: DetailPhotoViewModel

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
        viewModel = DetailPhotoViewModel(saveGalleryPhoto, deleteGalleryPhoto, getGalleryPhotoById)
    }

    @Test
    fun `Observing livedata when viewModel is created with default value`() =
        runTest {
            //GIVEN
            viewModel.statusFavorite.observeForever(observer)
            //WHEN
            //THEN
            assert(viewModel.statusFavorite.value == false)
        }

    @Test
    fun `when viewModel called isPhotoInDb then return if it has already been stored`() =
        runTest {
            //GIVEN
            val photo = mockPhoto.copy(nasa_id = "AS1")
            whenever(getGalleryPhotoById.invoke("AS1")).thenReturn(photo)
            viewModel.statusFavorite.observeForever(observer)
            //WHEN
            viewModel.checkIfPhotoSaved(photo.toGalleryFramework())
            //THEN
            assert(viewModel.statusFavorite.value == true)
        }

    @Test
    fun `when viewModel called isPhotoInDb then return null`() =
        runTest {
            //GIVEN
            val photo = mockPhoto.copy(nasa_id = "AS1")
            whenever(getGalleryPhotoById.invoke("AS1")).thenReturn(null)
            viewModel.statusFavorite.observeForever(observer)
            //WHEN
            viewModel.checkIfPhotoSaved(photo.toGalleryFramework())
            //THEN
            assert(viewModel.statusFavorite.value == false)
        }

    @Test
    fun `when viewModel called changeSaveStatusOfPhoto and remove favorite photo from database`() =
        runTest {
            //GIVEN
            val photo = mockPhoto.copy(nasa_id = "AS1")
            whenever(getGalleryPhotoById.invoke("AS1")).thenReturn(photo)
            whenever(deleteGalleryPhoto.invoke(photo)).thenReturn(Unit)
            //whenever(saveGalleryPhoto.invoke(photo)).thenReturn(Unit)
            viewModel.statusFavorite.observeForever(observer)
            //WHEN
            viewModel.changeSaveStatusOfPhoto(photo.toGalleryFramework())
            //THEN
            assert(viewModel.statusFavorite.value == false)
        }

    @Test
    fun `when viewModel called changeSaveStatusOfPhoto and add favorite photo to database`() =
        runTest {
            //GIVEN
            val photo = mockPhoto.copy(nasa_id = "AS1")
            whenever(getGalleryPhotoById.invoke("AS1")).thenReturn(null)
            //whenever(deleteGalleryPhoto.invoke(photo)).thenReturn(Unit)
            whenever(saveGalleryPhoto.invoke(photo)).thenReturn(Unit)
            viewModel.statusFavorite.observeForever(observer)
            //WHEN
            viewModel.changeSaveStatusOfPhoto(photo.toGalleryFramework())
            //THEN
            assert(viewModel.statusFavorite.value == true)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}