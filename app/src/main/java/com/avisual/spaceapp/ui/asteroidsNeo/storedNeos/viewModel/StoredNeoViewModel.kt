package com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.ui.common.ScopeViewModel
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.usecases.GetStoredNeos
import com.avisual.usecases.RemoveNeo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StoredNeoViewModel(
    private var getStoredNeos: GetStoredNeos,
    private var removeNeo: RemoveNeo
) : ViewModel() {

    private val _storedNeos = MutableLiveData<List<Neo>?>(null)
    val asteroidsSaved: LiveData<List<Neo>?> get() = _storedNeos

    init {
        startCollectingNeos()
    }


    fun startCollectingNeos() {

        viewModelScope.launch {
            getStoredNeos.invoke()?.collect { listDomainNeo ->
                _storedNeos.value = listDomainNeo.map { domainNeo -> domainNeo.toFrameworkNeo() }
            }
        }
    }

    fun removeAsteroidSaved(asteroid: Neo) {
        viewModelScope.launch {
            removeNeo.invoke(asteroid.toDomainNeo())
        }
    }
}