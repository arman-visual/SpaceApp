package com.avisual.spaceapp.ui.gallery.showGallery.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.spaceapp.defaultFakeGalleryPhotos
import com.avisual.spaceapp.initMockedDI
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel.GalleryUi
import com.avisual.usecases.GetGalleryPhotosByKeywordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
class ShowGalleryIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<GalleryUi>

    private lateinit var viewModel: ShowGalleryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            factory { ShowGalleryViewModel(get()) }
            factory { GetGalleryPhotosByKeywordUseCase(get()) }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `initial data is loaded from server by default keyword NASA`() {
        //GIVEN
        viewModel.model.observeForever(observer)
        //WHEN
        //THEN
        verify(observer).onChanged(GalleryUi.Content(defaultFakeGalleryPhotos))
    }

    @Test
    fun `when search photo from server by keyword`() {
        //GIVEN
        viewModel.model.observeForever(observer)
        //WHEN
        viewModel.findPhotosByKeyword("Mars")
        //THEN
        verify(observer, times(2)).onChanged(GalleryUi.Content(defaultFakeGalleryPhotos))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}