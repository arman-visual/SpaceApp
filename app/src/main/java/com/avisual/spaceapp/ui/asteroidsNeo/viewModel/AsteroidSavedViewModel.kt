package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.LiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.repository.NeoRepository
import kotlinx.coroutines.launch

class AsteroidSavedViewModel(var neoRepository: NeoRepository) : ScopeViewModel() {

    val asteroidsSaved: LiveData<List<Neo>> get() = neoRepository.getAllAsteroidsSaved()

    fun removeAsteroidSaved(asteroid: Neo){
        launch {
            neoRepository.removeAsteroid(asteroid)
        }
    }
}