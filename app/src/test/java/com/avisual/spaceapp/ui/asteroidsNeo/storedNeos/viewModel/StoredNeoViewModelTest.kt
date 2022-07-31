package com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel.StoredNeoViewModel.StoredNeoUi
import com.avisual.usecases.GetStoredNeosUseCase
import com.avisual.usecases.RemoveNeoUseCase
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
class StoredNeoViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var removeNeoUseCase: RemoveNeoUseCase

    @Mock
    private lateinit var getStoredNeosUseCase: GetStoredNeosUseCase

    @Mock
    private lateinit var observer: Observer<StoredNeoUi>

    private lateinit var viewModel: StoredNeoViewModel

    private val mockNeo = Neo(
        "AST1",
        "Apofis",
        true,
        2.3,
        "www.neo.org",
        300.00,
        550.00,
        "200km/s",
        "222km/s",
        "234234.23423",
        "234234234",
        "1000.000",
        "22-01-2022"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = StoredNeoViewModel(getStoredNeosUseCase, removeNeoUseCase)
    }

    @Test
    fun `when viewModel is created then return stored neos`() =
        runTest {
            //GIVEN
            val storedNeos = listOf(mockNeo.copy(id = "S1"))
            whenever(getStoredNeosUseCase.invoke()).thenReturn(flowOf(storedNeos))
            viewModel.model.observeForever(observer)
            //WHEN
            viewModel.getStoredNeosFromDb()
            //THEN
            verify(observer).onChanged(StoredNeoUi.Content(storedNeos.map { it.toFrameworkNeo() }))
            assert(viewModel.model.value == StoredNeoUi.Content(storedNeos.map { it.toFrameworkNeo() }))
        }

    @Test
    fun `when viewModel is created then return null`() =
        runTest {
            //GIVEN
            whenever(getStoredNeosUseCase.invoke()).thenReturn(null)
            viewModel.model.observeForever(observer)
            //WHEN
            viewModel.getStoredNeosFromDb()
            //THEN
            assert(viewModel.model.value == null)
        }

    @Test
    fun `when pressed removed storedNeo then return remove from database`()=
        runTest {
            //GIVEN
            val storedNeo = mockNeo.copy(id = "S2")
            whenever(removeNeoUseCase.invoke(storedNeo)).thenReturn(Unit)
            viewModel.model.observeForever(observer)
            //WHEN
            viewModel.removeAsteroidSaved(storedNeo.toFrameworkNeo())
            //THEN
            verify(removeNeoUseCase).invoke(storedNeo)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}