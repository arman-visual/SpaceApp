package com.avisual.spaceapp.ui.mainMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.usecases.GetCurrentRegionUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val getCurrentRegionUseCase: GetCurrentRegionUseCase) :
    ViewModel() {

    private var _language = MutableLiveData<String>()
    val language: LiveData<String> get() = _language

     fun getCurrentLocation() {
        viewModelScope.launch {
            val result = getCurrentRegionUseCase()
            _language.postValue(result)
        }
    }
}

