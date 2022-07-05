package com.avisual.spaceapp.ui.gallery.detailPhoto.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.spaceapp.fakePhoto
import com.avisual.spaceapp.initMockedDI
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetGalleryPhotoById
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
class DetailPhotoIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Boolean>

    private lateinit var viewModel: DetailPhotoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            viewModel {
                DetailPhotoViewModel(
                    SaveGalleryPhoto(get()),
                    DeleteGalleryPhoto(get()), GetGalleryPhotoById(get())
                )
            }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `when check if the current photo is favorite`() {
        //GIVEN
        viewModel.statusFavorite.observeForever(observer)
        //WHEN
        viewModel.checkIfPhotoSaved(fakePhoto.toGalleryFramework().copy(nasa_id = "S2"))
        //THEN
        verify(observer, times(1)).onChanged(viewModel.statusFavorite.value)
        assert(viewModel.statusFavorite.value == true)
    }

    @Test
    fun `when pressed favorite`() {
        //GIVEN
        viewModel.statusFavorite.observeForever(observer)
        //WHEN
        viewModel.changeSaveStatusOfPhoto(fakePhoto.toGalleryFramework().copy(nasa_id = "S2"))
        //THEN
        verify(observer, times(2)).onChanged(viewModel.statusFavorite.value)
        assert(viewModel.statusFavorite.value == false)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}