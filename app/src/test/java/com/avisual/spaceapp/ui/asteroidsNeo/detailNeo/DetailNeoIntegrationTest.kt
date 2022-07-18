package com.avisual.spaceapp.ui.asteroidsNeo.detailNeo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.spaceapp.fakeNeo
import com.avisual.spaceapp.initMockedDI
import com.avisual.usecases.GetNeoByIdUseCase
import com.avisual.usecases.RemoveNeoUseCase
import com.avisual.usecases.SaveNeoInDbUseCase
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
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailNeoIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Boolean>

    private lateinit var viewModel: DetailNeoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            viewModel { DetailNeoViewModel(SaveNeoInDbUseCase(get()), GetNeoByIdUseCase(get()), RemoveNeoUseCase(get())) }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `observing Livedata finds neos`() {
        //GIVEN
        viewModel.statusDb.observeForever(observer)
        //WHEN
        viewModel.checkIfPhotoSaved(fakeNeo.copy(id = "N2").toFrameworkNeo())
        //THEN
        verify(observer).onChanged(true)
    }

    @Test
    fun `observing Livedata when press favorite toggle then change state`() {
        //GIVEN
        viewModel.statusDb.observeForever(observer)
        //WHEN
        viewModel.changeSaveStatusOfPhoto(fakeNeo.copy(id = "N2").toFrameworkNeo())
        //THEN
        verify(observer).onChanged(false)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}