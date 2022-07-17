package com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.data.toFrameworkRover
import com.avisual.usecases.GetRoverPhotosByDate
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ShowPhotosViewModel(private var getRoverPhotosByDate: GetRoverPhotosByDate) :
    ViewModel() {
    private val _model = MutableLiveData<ShowPhotosUi>()
    val model: LiveData<ShowPhotosUi> get() = _model

    fun findPhotosByDate(date: String) {
        viewModelScope.launch {
            _model.value = ShowPhotosUi.Loading
            try {
                val response = getRoverPhotosByDate.invoke(date)
                _model.value =
                    ShowPhotosUi.Content(response?.map { photoDomain -> photoDomain.toFrameworkRover() })
            } catch (exception: HttpException) {
                _model.value = ShowPhotosUi.Content(emptyList())
            }
        }
    }

    sealed class ShowPhotosUi {
        object Loading : ShowPhotosUi()
        data class Content(val photos: List<PhotoRover>? = null) : ShowPhotosUi()
    }
}

