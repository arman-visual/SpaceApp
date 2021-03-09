package com.avisual.spaceapp.ui.searchGallery.viewModel

import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class DetailPhotoViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    fun saveFavoritePhoto(photoGallery: PhotoGallery) {
        launch {
            photoGalleryRepository.saveFavoritePhoto(photoGallery)
        }
    }
}