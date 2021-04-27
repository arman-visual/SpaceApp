package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.server.toDomainNeo
import com.avisual.spaceapp.server.toFrameworkNeo
import com.avisual.usecases.GetStoredNeos
import com.avisual.usecases.RemoveNeo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StoredNeoViewModel(
    private var getStoredNeos: GetStoredNeos,
    private var removeNeo: RemoveNeo
) : ScopeViewModel() {

    private val _storedNeos = MutableLiveData<List<Neo>>(emptyList())
    val asteroidsSaved: LiveData<List<Neo>> get() = _storedNeos

    init {
        startCollectingNeos()
    }

    private fun startCollectingNeos() {

        launch {
            getStoredNeos.invoke().collect { listDomainNeo ->
                _storedNeos.value = listDomainNeo.map { domainNeo -> domainNeo.toFrameworkNeo() }
            }
        }
    }

    fun removeAsteroidSaved(asteroid: Neo) {
        launch {
            removeNeo.invoke(asteroid.toDomainNeo())
        }
    }
}