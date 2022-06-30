package com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetAllStoredPhotos
import com.avisual.usecases.SaveGalleryPhoto
import kotlinx.coroutines.launch

class SavedPhotosViewModel(
    private val saveGalleryPhoto: SaveGalleryPhoto,
    private val deleteGalleryPhoto: DeleteGalleryPhoto,
    private val getAllStoredPhotos: GetAllStoredPhotos
) :
    ViewModel() {

    private val _modelSavedPhotos = MutableLiveData<SavedPhotosUi>(null)
    val modelSavedPhotos: LiveData<SavedPhotosUi> get() = _modelSavedPhotos

    init {
        startCollectingPhotos()
    }

    fun startCollectingPhotos() {
        viewModelScope.launch {
            getAllStoredPhotos.invoke()?.collect { listPhotoGalleryDomain ->
                _modelSavedPhotos.value = SavedPhotosUi.Content(listPhotoGalleryDomain.map { it.toGalleryFramework() })
            }
        }
    }

    fun deletePhoto(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            deleteGalleryPhoto.invoke(photoGallery.toGalleryDomain())
        }
    }

    fun savePhoto(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            saveGalleryPhoto.invoke(photoGallery.toGalleryDomain())
        }
    }

    sealed class SavedPhotosUi {
        data class Content(var storedPhotos: List<PhotoGallery>? = null) : SavedPhotosUi()
    }
}