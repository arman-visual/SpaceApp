package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.repository.PhotoGalleryRepository


class DetailPhotoViewModelFactory constructor(
    private val photoGalleryRepository: PhotoGalleryRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DetailPhotoViewModel(photoGalleryRepository) as T
}
