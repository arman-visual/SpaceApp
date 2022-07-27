package com.avisual.spaceapp.ui.asteroidsNeo.detailNeo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.usecases.GetNeoById
import com.avisual.usecases.RemoveNeo
import com.avisual.usecases.SaveNeoInDb
import kotlinx.coroutines.launch

class DetailNeoViewModel(
    var saveNeoInDb: SaveNeoInDb,
    var getNeoById: GetNeoById,
    var removeNeo: RemoveNeo
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
        return getNeoById.invoke(neo.id) != null
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
            saveNeoInDb.invoke(neo.toDomainNeo())
        }
    }

    private fun deletePhotoInDB(neo: Neo) {
        viewModelScope.launch {
            removeNeo.invoke(neo.toDomainNeo())
        }
    }
}