package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.repository.NeoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailNeoViewModel(var neoRepository: NeoRepository) : ScopeViewModel() {

    private val _statusDb = MutableLiveData(false)
    val statusDb: LiveData<Boolean>
        get() = _statusDb


    fun checkIfPhotoSaved(asteroid: Neo) {
        launch(Dispatchers.IO) {
            _statusDb.postValue(isPhotoInDB(asteroid))
        }
    }

    private suspend fun isPhotoInDB(asteroid: Neo): Boolean {
        return neoRepository.getAsteroidById(asteroid.id) != null
    }

    fun changeSaveStatusOfPhoto(asteroid: Neo) {
        launch(Dispatchers.IO) {
            val newFavoriteStatus = if (isPhotoInDB(asteroid)) {
                deletePhotoInDB(asteroid)
                false
            } else {
                savePhotoInDb(asteroid)
                true
            }
            _statusDb.postValue(newFavoriteStatus)
        }
    }

    private fun savePhotoInDb(asteroid: Neo) {
        launch {
            neoRepository.insertAsteroid(asteroid)
        }
    }

    private fun deletePhotoInDB(asteroid: Neo) {
        launch {
            neoRepository.removeAsteroid(asteroid)
        }
    }
}