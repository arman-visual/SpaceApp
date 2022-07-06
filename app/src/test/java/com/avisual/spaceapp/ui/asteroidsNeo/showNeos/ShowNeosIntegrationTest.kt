package com.avisual.spaceapp.ui.asteroidsNeo.showNeos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.spaceapp.defaultFakeNeoPhotos
import com.avisual.spaceapp.initMockedDI
import com.avisual.spaceapp.ui.asteroidsNeo.showNeos.ShowNeoViewModel.ShowNeoUi
import com.avisual.usecases.GetAllNeoByDate
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
class ShowNeosIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<ShowNeoUi>

    private lateinit var viewModel: ShowNeoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            factory { ShowNeoViewModel(GetAllNeoByDate(get())) }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `observing Livedata finds photos`() {
        //GIVEN
        viewModel.model.observeForever(observer)
        //WHEN
        viewModel.getAsteroidsByOnlyDate("12-02-12")
        //THEN
        verify(
            observer,
            times(1)
        ).onChanged(ShowNeoUi.Content(defaultFakeNeoPhotos.map { it.toFrameworkNeo() }))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}