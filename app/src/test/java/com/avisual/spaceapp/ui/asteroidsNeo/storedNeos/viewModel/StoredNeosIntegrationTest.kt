package com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.data.source.NeoLocalDataSource
import com.avisual.spaceapp.FakeNeoLocalDataSource
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.spaceapp.defaultFakeNeoPhotos
import com.avisual.spaceapp.fakeNeo
import com.avisual.spaceapp.initMockedDI
import com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel.StoredNeoViewModel.StoredNeoUi
import com.avisual.usecases.GetStoredNeosUseCase
import com.avisual.usecases.RemoveNeoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
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
class StoredNeosIntegrationTest : KoinTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<StoredNeoUi>

    private lateinit var viewModel: StoredNeoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val vmModule = module {
            viewModel { StoredNeoViewModel(GetStoredNeosUseCase(get()), RemoveNeoUseCase(get())) }
        }
        initMockedDI(vmModule)
        viewModel = get()
    }

    @Test
    fun `observing Livedata when started then finds neos from database`() =
        runTest {
            //GIVEN
            viewModel.model.observeForever(observer)
            //WHEN
            //THEN
            verify(
                observer
            ).onChanged(StoredNeoUi.Content(defaultFakeNeoPhotos.map { it.toFrameworkNeo() }))
        }

    @Test
    fun `observing Livedata when loading neos from database`() =
        runTest {
            //GIVEN
            viewModel.model.observeForever(observer)
            //WHEN
            viewModel.getStoredNeosFromDb()
            //THEN
            verify(observer, times(2)).onChanged(StoredNeoUi.Content(defaultFakeNeoPhotos.map { it.toFrameworkNeo() }))
        }

    @Test
    fun `observing Livedata when remove neo from database`() =
        runTest {
            //GIVEN
            viewModel.model.observeForever(observer)
            val localDataSource = get<NeoLocalDataSource>() as FakeNeoLocalDataSource
            //WHEN
            viewModel.removeAsteroidSaved(fakeNeo.copy(id = "N1").toFrameworkNeo())
            viewModel.getStoredNeosFromDb()
            //THEN
            assert(viewModel.model.value == StoredNeoUi.Content(localDataSource.storedNeos.map { it.toFrameworkNeo() }))
            verify(observer).onChanged(StoredNeoUi.Content(localDataSource.storedNeos.map { it.toFrameworkNeo() }))
        }

    @After
    fun tearDown() {
        stopKoin()
    }
}