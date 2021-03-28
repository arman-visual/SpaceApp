package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.asteroidsNeoWsResponse.toNeo
import com.avisual.spaceapp.repository.NeoRepository
import kotlinx.coroutines.launch

class ShowNeoViewModel(var neoRepository: NeoRepository) : ScopeViewModel() {

    private val _listsAsteroids = MutableLiveData<List<Neo>>()
    val listAsteroids: LiveData<List<Neo>> get() = _listsAsteroids

    fun getAsteroidsByDate(dateStart: String, dateFinal: String, apiKey: String) {

        var totalAsteroids = mutableListOf<Neo>()

        launch {
            val response = neoRepository.findPhotosRoverFromServer(dateStart, dateFinal, apiKey)

            val totalDays = response.registerDay.size
            var keys = response.registerDay.keys.sorted()

            for (i in 0 until totalDays) {
                response.registerDay.getValue(keys[i]).map { neo -> totalAsteroids.add(neo.toNeo(keys[i])) }
            }
            println("Total de objetos almacenados : ${totalAsteroids.toList().size}")
            _listsAsteroids.postValue(totalAsteroids)
        }
    }

}