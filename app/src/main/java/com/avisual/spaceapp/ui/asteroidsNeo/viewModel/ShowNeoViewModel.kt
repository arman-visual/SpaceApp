package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.NearEarthObjectResult
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.toFrameworkNeo
import com.avisual.spaceapp.repository.NeoRepository
import kotlinx.coroutines.launch

class ShowNeoViewModel(var neoRepository: NeoRepository) : ScopeViewModel() {

    private val _listsAsteroids = MutableLiveData<ShowNeoUi>()
    val listAsteroids: LiveData<ShowNeoUi> get() = _listsAsteroids

    fun getAsteroidsByOnlyDate(dateStart: String) {

        var totalAsteroids = mutableListOf<Neo>()

        launch {
            _listsAsteroids.value = ShowNeoUi.Loading

            val response = neoRepository.findNeoByOnlyStartDate(dateStart)

            convertToOneListDate(response, totalAsteroids)

            _listsAsteroids.value = ShowNeoUi.Content(totalAsteroids)
        }
    }

    private fun convertToOneListDate(
        response: NearEarthObjectResult,
        totalAsteroids: MutableList<Neo>
    ) {
        val totalDays = response.registerDay.size
        val keys = response.registerDay.keys.sorted()

        for (i in 0 until totalDays) {
            response.registerDay.getValue(keys[i])
                .map { neo -> totalAsteroids.add(neo.toFrameworkNeo(keys[i])) }
        }
    }

}

sealed class ShowNeoUi {
    object Loading : ShowNeoUi()
    class Content(val asteroids: List<Neo>) : ShowNeoUi()
}