package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.repository.NeoRepository
import kotlinx.coroutines.launch

class ShowNeoViewModel(var neoRepository: NeoRepository) : ScopeViewModel() {

    fun getAsteroidsByDate(dateStart: String, dateFinal:String, apiKey:String){
        launch {
            var response = neoRepository.findPhotosRoverFromServer(dateStart, dateFinal, apiKey)
            var orderDay = response.registerDay.toSortedMap()
        }
    }

}