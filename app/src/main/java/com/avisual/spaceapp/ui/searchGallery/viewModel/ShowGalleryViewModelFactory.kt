package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.GetGalleryPhotosByKeyword

class ShowGalleryViewModelFactory constructor(
    private val getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ShowGalleryViewModel(getGalleryPhotosByKeyword) as T
}