package com.avisual.spaceapp.ui.gallery.detailPhoto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetGalleryPhotoById
import com.avisual.usecases.SaveGalleryPhoto
import kotlinx.coroutines.launch

class DetailPhotoViewModel(
    private val saveGalleryPhoto: SaveGalleryPhoto,
    private val deleteGalleryPhoto: DeleteGalleryPhoto,
    private val getGalleryPhotoById: GetGalleryPhotoById
) :
    ViewModel() {

    private val _statusFavorite = MutableLiveData(false)
    val statusFavorite: LiveData<Boolean>
        get() = _statusFavorite

    fun checkIfPhotoSaved(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            _statusFavorite.postValue(isPhotoInDB(photoGallery))
        }
    }

    private suspend fun isPhotoInDB(photoGallery: PhotoGallery): Boolean {
        return getGalleryPhotoById.invoke(photoGallery.nasa_id) != null
    }

    private fun savePhotoInDb(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            saveGalleryPhoto.invoke(photoGallery.toGalleryDomain())
        }
    }

    private fun deletePhotoInDB(photoGallery: PhotoGallery) {
        viewModelScope.launch {
            deleteGalleryPhoto.invoke(photoGallery.toGalleryDomain())
        }
    }

    fun changeSaveStatusOfPhoto(photoGallery: PhotoGallery) {
        viewModelScope.launch {
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