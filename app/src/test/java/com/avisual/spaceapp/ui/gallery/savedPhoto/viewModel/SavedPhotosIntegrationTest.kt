package com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.data.source.GalleryLocalDataSource
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.*
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel.SavedPhotosViewModel.SavedPhotosUi
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetAllStoredPhotos
import com.avisual.usecases.SaveGalleryPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SavedPhotosIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<SavedPhotosUi>

    private lateinit var viewModel: SavedPhotosViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            viewModel {
                SavedPhotosViewModel(
                    SaveGalleryPhoto(get()),
                    DeleteGalleryPhoto(get()), GetAllStoredPhotos(get())
                )
            }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `when get all stored photos from database`() {
        //GIVEN
        viewModel.modelSavedPhotos.observeForever(observer)
        //WHEN
        //THEN
        verify(observer, times(1))
            .onChanged(SavedPhotosUi.Content(defaultStoredPhotos.map { it.toGalleryFramework() }))
    }

    @Test
    fun `when deleted photo to local database`() {
        //GIVEN
        viewModel.modelSavedPhotos.observeForever(observer)
        val localDataSource = get<GalleryLocalDataSource>() as FakeGalleryLocalDataSource
        //WHEN
        viewModel.deletePhoto(fakePhoto.copy(nasa_id = "S2").toGalleryFramework())
        //THEN
        verify(observer, times(1)).onChanged(SavedPhotosUi.Content(localDataSource.storedPhotos.map { it.toGalleryFramework() }))
    }

    @Test
    fun `when saved photo to local database`() {
        //GIVEN
        viewModel.modelSavedPhotos.observeForever(observer)
        val localDataSource = get<GalleryLocalDataSource>() as FakeGalleryLocalDataSource
        //WHEN
        viewModel.savePhoto(fakePhoto.copy(nasa_id = "S3").toGalleryFramework())
        //THEN
        verify(observer, times(1))
            .onChanged(SavedPhotosUi.Content(localDataSource.storedPhotos.map { it.toGalleryFramework() }))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}