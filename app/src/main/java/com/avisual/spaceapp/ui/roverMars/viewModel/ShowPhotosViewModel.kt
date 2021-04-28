package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoRover
import com.avisual.spaceapp.server.toFrameworkRover
import com.avisual.usecases.GetRoverPhotosByDate
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ShowPhotosViewModel(var getRoverPhotosByDate: GetRoverPhotosByDate) :
    ScopeViewModel() {

    private val _model = MutableLiveData<ShowPhotosUi>()
    val model: LiveData<ShowPhotosUi> get() = _model

    fun findPhotosByDate(date: String) {
        launch {
            _model.value = ShowPhotosUi.Loading
            try {
                val response = getRoverPhotosByDate.invoke(date)
                _model.value =
                    ShowPhotosUi.Content(response.map { photoDomain -> photoDomain.toFrameworkRover() })
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

