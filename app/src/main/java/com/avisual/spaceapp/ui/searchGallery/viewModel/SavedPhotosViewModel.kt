package com.avisual.spaceapp.ui.searchGallery.viewModel

import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    val saveLivePhotos = photoGalleryRepository.getAllPhotosLiveData()

    fun deletePhoto(photoGallery: PhotoGallery) {
        launch {
            photoGalleryRepository.deletePhoto(photoGallery)
        }
    }

    fun savePhoto(photoGallery: PhotoGallery) {
        launch {
            photoGalleryRepository.savePhoto(photoGallery)
        }
    }
}