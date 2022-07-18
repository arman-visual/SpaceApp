package com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.data.toFrameworkRover
import com.avisual.usecases.GetRoverPhotosByDateUseCase
import kotlinx.coroutines.launch

class ShowPhotosViewModel(private var getRoverPhotosByDateUseCase: GetRoverPhotosByDateUseCase) :
    ViewModel() {
    private val _model = MutableLiveData<ShowPhotosUi>()
    val model: LiveData<ShowPhotosUi> get() = _model

    fun findPhotosByDate(date: String) {
        viewModelScope.launch {
            _model.value = ShowPhotosUi.Loading
            val response = getRoverPhotosByDateUseCase.invoke(date)
            _model.value =
                ShowPhotosUi.Content(response.map { photoDomain -> photoDomain.toFrameworkRover() })
        }
    }

    sealed class ShowPhotosUi {
        object Loading : ShowPhotosUi()
        data class Content(val photos: List<PhotoRover> = emptyList()) : ShowPhotosUi()
    }
}

