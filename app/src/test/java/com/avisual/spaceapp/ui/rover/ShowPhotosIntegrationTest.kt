package com.avisual.spaceapp.ui.rover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.spaceapp.data.toFrameworkRover
import com.avisual.spaceapp.defaultFakeRoverPhotos
import com.avisual.spaceapp.initMockedDI
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel.*
import com.avisual.usecases.GetRoverPhotosByDate
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
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ShowPhotosIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<ShowPhotosUi>

    private lateinit var viewModel: ShowPhotosViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            factory { ShowPhotosViewModel(GetRoverPhotosByDate(get())) }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `observing Livedata finds photos from server`() {
        //GIVEN
        viewModel.model.observeForever(observer)
        //WHEN
        viewModel.findPhotosByDate("23-02-12")
        //THEN
        verify(observer).onChanged(ShowPhotosUi.Content(defaultFakeRoverPhotos.map { it.toFrameworkRover() }))
        assert(viewModel.model.value == ShowPhotosUi.Content(defaultFakeRoverPhotos.map { it.toFrameworkRover() }))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}