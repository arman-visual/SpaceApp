package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetGalleryPhotoById
import com.avisual.usecases.SaveGalleryPhoto


class DetailPhotoViewModelFactory constructor(
    private val saveGalleryPhoto: SaveGalleryPhoto,
    private val deleteGalleryPhoto: DeleteGalleryPhoto,
    private val getGalleryPhotoById: GetGalleryPhotoById
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DetailPhotoViewModel(saveGalleryPhoto, deleteGalleryPhoto, getGalleryPhotoById) as T
}
