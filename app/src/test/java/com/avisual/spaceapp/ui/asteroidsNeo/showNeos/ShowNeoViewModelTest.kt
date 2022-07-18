package com.avisual.spaceapp.ui.asteroidsNeo.showNeos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.spaceapp.ui.asteroidsNeo.showNeos.ShowNeoViewModel.*
import com.avisual.usecases.GetAllNeoByDateUseCase
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
class ShowNeoViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<ShowNeoUi>

    @Mock
    private lateinit var getAllNeoByDateUseCase: GetAllNeoByDateUseCase

    private lateinit var viewModel: ShowNeoViewModel

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
        viewModel = ShowNeoViewModel(getAllNeoByDateUseCase)
    }

    @Test
    fun `when search neos by date then returns neos`() =
        runTest {
            //GIVEN
            val neos = listOf(mockNeo)
            whenever(getAllNeoByDateUseCase.invoke("22-02-2022")).thenReturn(neos)
            viewModel.model.observeForever(observer)
            //WHEN
            viewModel.getAsteroidsByOnlyDate("22-02-2022")
            //THEN
            verify(observer).onChanged(ShowNeoUi.Content(neos.map { it.toFrameworkNeo() }))
        }

    @Test
    fun `when search neos by date then returns null`() =
        runTest {
            //GIVEN
            val neos = listOf(mockNeo)
            whenever(getAllNeoByDateUseCase.invoke("22-02-2022")).thenReturn(emptyList())
            viewModel.model.observeForever(observer)
            //WHEN
            viewModel.getAsteroidsByOnlyDate("22-02-2022")
            //THEN
            verify(observer).onChanged(ShowNeoUi.Content(emptyList()))
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}