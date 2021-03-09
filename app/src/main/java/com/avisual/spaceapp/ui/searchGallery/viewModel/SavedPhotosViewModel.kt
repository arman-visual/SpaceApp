package com.avisual.spaceapp.ui.searchGallery.viewModel

import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.repository.PhotoGalleryRepository

class SavedPhotosViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    val saveLivePhotos = photoGalleryRepository.getAllPhotosLiveData()

}