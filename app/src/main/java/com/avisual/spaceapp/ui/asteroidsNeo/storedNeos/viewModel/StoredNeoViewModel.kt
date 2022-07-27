package com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.usecases.GetStoredNeos
import com.avisual.usecases.RemoveNeo
import kotlinx.coroutines.launch

class StoredNeoViewModel(
    private var getStoredNeos: GetStoredNeos,
    private var removeNeo: RemoveNeo
) : ViewModel() {

    private val _model = MutableLiveData<StoredNeoUi>()
    val model: LiveData<StoredNeoUi> get() = _model

    init {
        getStoredNeosFromDb()
    }

    fun getStoredNeosFromDb() {

        viewModelScope.launch {
            getStoredNeos.invoke()?.collect { listDomainNeo ->
                _model.value =
                    StoredNeoUi.Content(listDomainNeo.map { domainNeo -> domainNeo.toFrameworkNeo() })
            }
        }
    }

    fun removeAsteroidSaved(asteroid: Neo) {
        viewModelScope.launch {
            removeNeo.invoke(asteroid.toDomainNeo())
        }
    }

    sealed class StoredNeoUi {
        data class Content(val neos: List<Neo>? = null) : StoredNeoUi()
    }
}