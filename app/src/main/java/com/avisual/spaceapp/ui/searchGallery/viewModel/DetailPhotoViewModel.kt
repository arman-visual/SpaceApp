package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class DetailPhotoViewModel(
    private val photoId: String,
    private val photoGalleryRepository: PhotoGalleryRepository
) :
    ScopeViewModel() {


    private val _photoSavedGallery = MutableLiveData<PhotoGallery>()

    init {
        getFindByNasaId(photoId)
    }

    fun savePhoto(photoGallery: PhotoGallery) {
        launch {
            photoGalleryRepository.savePhoto(photoGallery)
        }
    }

    fun getFindByNasaId(photoId: String): Boolean {
        launch {
            _photoSavedGallery.postValue(photoGalleryRepository.getFindById(photoId))
        }
        return _photoSavedGallery.value?.nasa_id.equals(photoId)
    }
}