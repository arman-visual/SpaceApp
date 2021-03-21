package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoCuriosity
import com.avisual.spaceapp.model.nasaRoverResponse.convertToPhotoCuriosity
import com.avisual.spaceapp.repository.PhotoCuriosityRepository
import kotlinx.coroutines.launch

class ShowPhotosViewModel(var photoCuriosityRepository: PhotoCuriosityRepository) :
    ScopeViewModel() {

    private val _photosCuriosity = MutableLiveData<List<PhotoCuriosity>>()
    val photosCuriosity: LiveData<List<PhotoCuriosity>> get() = _photosCuriosity

    fun findPhotosByDate(date: String, apiKey: String) {
        launch {
            var response = photoCuriosityRepository.findPhotosRoverFromServer(date, apiKey)
            _photosCuriosity.value = response.photos.map { it.convertToPhotoCuriosity() }
        }
    }
}


