package com.avisual.spaceapp.ui.rover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.PhotoRover
import com.avisual.spaceapp.data.toFrameworkRover
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel.ShowPhotosUi
import com.avisual.usecases.GetRoverPhotosByDateUseCase
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
class ShowPhotosViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getRoverPhotosByDateUseCase: GetRoverPhotosByDateUseCase

    @Mock
    private lateinit var observe: Observer<ShowPhotosUi>

    private lateinit var viewModel: ShowPhotosViewModel

    private val mockRover = PhotoRover(
        1,
        "www.nasa.org",
        "afternoon in mars",
        "fronta",
        1212,
        "12-02-2021",
        "",
        "",
        "Curiosity",
        "ON",
        1
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ShowPhotosViewModel(getRoverPhotosByDateUseCase)
    }

    @Test
    fun `when pressed search button with date then returns rover photos`() =
        runTest {
            //GIVEN
            val photosRover = listOf(mockRover.copy(id = 2))
            whenever(getRoverPhotosByDateUseCase.invoke("22-02-2021")).thenReturn(photosRover)
            viewModel.model.observeForever(observe)
            //THEN
            viewModel.findPhotosByDate("22-02-2021")
            //WHEN
            assert(viewModel.model.value == ShowPhotosUi.Content(photosRover.map { it.toFrameworkRover() }))
            verify(observe).onChanged(ShowPhotosUi.Content(photosRover.map { it.toFrameworkRover() }))
        }

    @Test
    fun `when pressed search button with date then returns null`() =
        runTest {
            //GIVEN
            whenever(getRoverPhotosByDateUseCase.invoke("22-02-2021")).thenReturn(null)
            viewModel.model.observeForever(observe)
            //THEN
            viewModel.findPhotosByDate("22-02-2021")
            //WHEN
            assert(viewModel.model.value == ShowPhotosUi.Content(null))
            verify(observe).onChanged(ShowPhotosUi.Content(null))
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}