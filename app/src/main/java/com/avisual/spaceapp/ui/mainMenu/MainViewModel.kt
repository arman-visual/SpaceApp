package com.avisual.spaceapp.ui.mainMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.usecases.GetCurrentRegionUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val getCurrentRegionUseCase: GetCurrentRegionUseCase) :
    ViewModel() {

    private var _currentRegion = MutableLiveData<String>()
    val currentRegion: LiveData<String> get() = _currentRegion

     fun getCurrentLocation() {
        viewModelScope.launch {
            val result = getCurrentRegionUseCase()
            _currentRegion.postValue(result)
        }
    }
}

