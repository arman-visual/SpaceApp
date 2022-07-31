package com.avisual.spaceapp.ui.asteroidsNeo.detailNeo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.usecases.GetNeoByIdUseCase
import com.avisual.usecases.RemoveNeoUseCase
import com.avisual.usecases.SaveNeoInDbUseCase
import kotlinx.coroutines.launch

class DetailNeoViewModel(
    var saveNeoInDbUseCase: SaveNeoInDbUseCase,
    var getNeoByIdUseCase: GetNeoByIdUseCase,
    var removeNeoUseCase: RemoveNeoUseCase
) : ViewModel() {

    private val _statusDb = MutableLiveData(false)
    val statusDb: LiveData<Boolean>
        get() = _statusDb


    fun checkIfPhotoSaved(neo: Neo) {
        viewModelScope.launch {
            _statusDb.postValue(isPhotoInDB(neo))
        }
    }

    private suspend fun isPhotoInDB(neo: Neo): Boolean {
        return getNeoByIdUseCase.invoke(neo.id) != null
    }

    fun changeSaveStatusOfPhoto(neo: Neo) {
        viewModelScope.launch {
            val newFavoriteStatus = if (isPhotoInDB(neo)) {
                deletePhotoInDB(neo)
                false
            } else {
                savePhotoInDb(neo)
                true
            }
            _statusDb.postValue(newFavoriteStatus)
        }
    }

    private fun savePhotoInDb(neo: Neo) {
        viewModelScope.launch {
            saveNeoInDbUseCase.invoke(neo.toDomainNeo())
        }
    }

    private fun deletePhotoInDB(neo: Neo) {
        viewModelScope.launch {
            removeNeoUseCase.invoke(neo.toDomainNeo())
        }
    }
}