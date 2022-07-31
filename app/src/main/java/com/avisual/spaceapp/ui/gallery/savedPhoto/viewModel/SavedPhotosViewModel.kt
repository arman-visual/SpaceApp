package com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.usecases.DeleteGalleryPhotoUseCase
import com.avisual.usecases.GetAllStoredPhotosUseCase
import com.avisual.usecases.SaveGalleryPhotoUseCase
import kotlinx.coroutines.launch

class SavedPhotosViewModel(
    private val saveGalleryPhotoUseCase: SaveGalleryPhotoUseCase,
    private val deleteGalleryPhotoUseCase: DeleteGalleryPhotoUseCase,
    private val getAllStoredPhotosUseCase: GetAllStoredPhotosUseCase
) :
    ViewModel() {

    private val _modelSavedPhotos = MutableLiveData<SavedPhotosUi>()
    val modelSavedPhotos: LiveData<SavedPhotosUi> get() = _modelSavedPhotos

    init {
        getPhotosFromDb()
    }

    fun getPhotosFromDb() {
        viewModelScope.launch {
            getAllStoredPhotosUseCase.invoke()?.collect { listPhotoGalleryDomain ->
                _modelSavedPhotos.value = SavedPhotosUi.Content(listPhotoGalleryDomain.map { it.toGalleryFramework() })
            }
        }
    }

    fun deletePhoto(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            deleteGalleryPhotoUseCase.invoke(photoGallery.toGalleryDomain())
        }
    }

    fun savePhoto(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            saveGalleryPhotoUseCase.invoke(photoGallery.toGalleryDomain())
        }
    }

    sealed class SavedPhotosUi {
        data class Content(var storedPhotos: List<PhotoGallery>? = null) : SavedPhotosUi()
    }
}