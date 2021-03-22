package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoRover
import com.avisual.spaceapp.model.nasaRoverResponse.convertToPhotoRover
import com.avisual.spaceapp.repository.PhotoRoverRepository
import kotlinx.coroutines.launch

class ShowPhotosViewModel(var photoRoverRepository: PhotoRoverRepository) :
    ScopeViewModel() {

    private val _photosRover = MutableLiveData<List<PhotoRover>>()
    val photosRover: LiveData<List<PhotoRover>> get() = _photosRover

    fun findPhotosByDate(date: String, apiKey: String) {
        launch {
            var response = photoRoverRepository.findPhotosRoverFromServer(date, apiKey)
            _photosRover.value = response.photos.map { it.convertToPhotoRover() }
        }
    }
}


