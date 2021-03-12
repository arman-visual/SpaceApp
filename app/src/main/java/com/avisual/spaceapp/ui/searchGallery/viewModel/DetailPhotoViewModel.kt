package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPhotoViewModel(
    private val photoGalleryRepository: PhotoGalleryRepository
) :
    ScopeViewModel() {

    private val _statusFavorite = MutableLiveData(false)
    val statusFavorite: LiveData<Boolean>
        get() = _statusFavorite

    fun checkIfPhotoSaved(photoGallery: PhotoGallery) {
        launch(Dispatchers.IO) {
            _statusFavorite.postValue(isPhotoInDB(photoGallery))
        }
    }

    private suspend fun isPhotoInDB(photoGallery: PhotoGallery): Boolean {
        return photoGalleryRepository.getFindByNasaId(photoGallery.nasa_id) != null
    }

    private fun savePhotoInDb(photoGallery: PhotoGallery) {
        launch {
            photoGalleryRepository.savePhoto(photoGallery)
        }
    }

    private fun deletePhotoInDB(photoGallery: PhotoGallery) {
        launch {
            photoGalleryRepository.deletePhoto(photoGallery)
        }
    }

    fun changeSaveStatusOfPhoto(photoGallery: PhotoGallery) {
        launch(Dispatchers.IO) {
            val newFavoriteStatus = if (isPhotoInDB(photoGallery)) {
                deletePhotoInDB(photoGallery)
                false
            } else {
                savePhotoInDb(photoGallery)
                true
            }
            _statusFavorite.postValue(newFavoriteStatus)
        }
    }

}