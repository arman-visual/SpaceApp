package com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.avisual.domain.Neo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.usecases.GetStoredNeos
import com.avisual.usecases.RemoveNeo
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
import com.avisual.spaceapp.data.model.Neo as FrameworkNeo

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoredNeoViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var removeNeo: RemoveNeo

    @Mock
    private lateinit var getStoredNeos: GetStoredNeos

    @Mock
    private lateinit var observer: Observer<List<FrameworkNeo>?>

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
        viewModel = StoredNeoViewModel(getStoredNeos, removeNeo)
    }

    @Test
    fun `when viewModel is created then return stored neos`() =
        runTest {
            //GIVEN
            val storedNeos = listOf(mockNeo.copy(id = "S1"))
            whenever(getStoredNeos.invoke()).thenReturn(flowOf(storedNeos))
            viewModel.asteroidsSaved.observeForever(observer)
            //WHEN
            viewModel.startCollectingNeos()
            //THEN
            verify(observer).onChanged(storedNeos.map { it.toFrameworkNeo() })
            assert(viewModel.asteroidsSaved.value == storedNeos.map { it.toFrameworkNeo() })
        }

    @Test
    fun `when viewModel is created then return null`() =
        runTest {
            //GIVEN
            whenever(getStoredNeos.invoke()).thenReturn(null)
            viewModel.asteroidsSaved.observeForever(observer)
            //WHEN
            viewModel.startCollectingNeos()
            //THEN
            assert(viewModel.asteroidsSaved.value == null)
        }

    @Test
    fun `when pressed removed storedNeo then return remove from database`()=
        runTest {
            //GIVEN
            val storedNeo = mockNeo.copy(id = "S2")
            whenever(removeNeo.invoke(storedNeo)).thenReturn(Unit)
            viewModel.asteroidsSaved.observeForever(observer)
            //WHEN
            viewModel.removeAsteroidSaved(storedNeo.toFrameworkNeo())
            //THEN
            verify(removeNeo).invoke(storedNeo)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}