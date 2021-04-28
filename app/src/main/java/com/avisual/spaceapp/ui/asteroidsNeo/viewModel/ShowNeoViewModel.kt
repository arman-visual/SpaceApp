package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.ui.common.ScopeViewModel
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.server.toFrameworkNeo
import com.avisual.usecases.GetAllNeoByDate
import kotlinx.coroutines.launch

class ShowNeoViewModel(private var getAllNeoByDate: GetAllNeoByDate) : ScopeViewModel() {

    private val _listsAsteroids = MutableLiveData<ShowNeoUi>()
    val listAsteroids: LiveData<ShowNeoUi> get() = _listsAsteroids

    fun getAsteroidsByOnlyDate(dateStart: String) {

        launch {
            _listsAsteroids.value = ShowNeoUi.Loading
            val response = getAllNeoByDate.invoke(dateStart).map { domainNeo ->
                domainNeo.toFrameworkNeo()
            }

            _listsAsteroids.value = ShowNeoUi.Content(response)
        }
    }
}

sealed class ShowNeoUi {
    object Loading : ShowNeoUi()
    class Content(val asteroids: List<Neo>) : ShowNeoUi()
}