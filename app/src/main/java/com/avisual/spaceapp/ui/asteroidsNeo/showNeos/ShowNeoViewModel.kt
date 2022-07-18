package com.avisual.spaceapp.ui.asteroidsNeo.showNeos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.toFrameworkNeo
import com.avisual.usecases.GetAllNeoByDate
import kotlinx.coroutines.launch

class ShowNeoViewModel(private var getAllNeoByDate: GetAllNeoByDate) : ViewModel() {

    private val _model = MutableLiveData<ShowNeoUi>()
    val model: LiveData<ShowNeoUi> get() = _model

    fun getAsteroidsByOnlyDate(dateStart: String) {

        viewModelScope.launch {
            _model.value = ShowNeoUi.Loading
            val response = getAllNeoByDate.invoke(dateStart).map { domainNeo ->
                domainNeo.toFrameworkNeo()
            }
            _model.value = ShowNeoUi.Content(response)
        }
    }

    sealed class ShowNeoUi {
        object Loading : ShowNeoUi()
        data class Content(val asteroids: List<Neo> = emptyList()) : ShowNeoUi()
    }
}