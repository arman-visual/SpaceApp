package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoRover
import com.avisual.spaceapp.model.nasaRoverResponse.convertToPhotoRover
import com.avisual.spaceapp.repository.PhotoRoverRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ShowPhotosViewModel(var photoRoverRepository: PhotoRoverRepository) :
    ScopeViewModel() {

    private val _model = MutableLiveData<ShowPhotosUi>()
    val model: LiveData<ShowPhotosUi> get() = _model

    fun findPhotosByDate(date: String, apiKey: String) {
        launch {
            _model.value = ShowPhotosUi.Loading
            try {
                val response = photoRoverRepository.findPhotosRoverFromServer(date, apiKey)
                _model.value =
                    ShowPhotosUi.Content(response.photos.map { it.convertToPhotoRover() })
            } catch (exception: HttpException) {
                _model.value = ShowPhotosUi.Content(emptyList())
            }
        }
    }
}

sealed class ShowPhotosUi {
    object Loading : ShowPhotosUi()
    class Content(val photos: List<PhotoRover>) : ShowPhotosUi()
}

