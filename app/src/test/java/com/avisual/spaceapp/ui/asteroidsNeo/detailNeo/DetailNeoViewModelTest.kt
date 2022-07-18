package com.avisual.spaceapp.ui.asteroidsNeo.detailNeo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.usecases.GetNeoByIdUseCase
import com.avisual.usecases.RemoveNeoUseCase
import com.avisual.usecases.SaveNeoInDbUseCase
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
class DetailNeoViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var saveNeoInDbUseCase: SaveNeoInDbUseCase

    @Mock
    private lateinit var getNeoByIdUseCase: GetNeoByIdUseCase

    @Mock
    private lateinit var removeNeoUseCase: RemoveNeoUseCase

    @Mock
    private lateinit var observer: Observer<Boolean>

    private lateinit var viewModel: DetailNeoViewModel

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
        viewModel = DetailNeoViewModel(saveNeoInDbUseCase, getNeoByIdUseCase, removeNeoUseCase)
    }

    @Test
    fun `when checked state of photo then return current state`() =
        runTest {
            //GIVEN
            val neo = mockNeo.copy(id = "NEO_TST_1")
            whenever(getNeoByIdUseCase.invoke(neo.id)).thenReturn(neo)
            viewModel.statusDb.observeForever(observer)
            //WHEN
            viewModel.checkIfPhotoSaved(neo.toFrameworkNeo())
            //THEN
            assert(viewModel.statusDb.value == true)
        }

    @Test
    fun `when checked state of photo then return null`() =
        runTest {
            //GIVEN
            val neo = mockNeo.copy(id = "NEO_TST_1")
            whenever(getNeoByIdUseCase.invoke(neo.id)).thenReturn(null)
            viewModel.statusDb.observeForever(observer)
            //WHEN
            viewModel.checkIfPhotoSaved(neo.toFrameworkNeo())
            //THEN
            assert(viewModel.statusDb.value == false)
        }

    @Test
    fun `when favorite clicked then save or remove of favorites`() =
        runTest {
            //GIVEN
            val neo = mockNeo.copy(id = "NEO_TST_1")
            whenever(getNeoByIdUseCase.invoke(neo.id)).thenReturn(neo)
            whenever(removeNeoUseCase.invoke(neo)).thenReturn(Unit)
            viewModel.statusDb.observeForever(observer)
            //WHEN
            viewModel.changeSaveStatusOfPhoto(neo.toFrameworkNeo())
            //THEN
            verify(removeNeoUseCase).invoke(neo)
        }

    @Test
    fun `when favorite clicked then remove of favorites`() =
        runTest {
            //GIVEN
            val neo = mockNeo.copy(id = "NEO_TST_1")
            whenever(getNeoByIdUseCase.invoke(neo.id)).thenReturn(null)
            whenever(saveNeoInDbUseCase.invoke(neo)).thenReturn(Unit)
            viewModel.statusDb.observeForever(observer)
            //WHEN
            viewModel.changeSaveStatusOfPhoto(neo.toFrameworkNeo())
            //THEN
            verify(saveNeoInDbUseCase).invoke(neo)
        }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}