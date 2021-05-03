package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetAllStoredPhotos
import com.avisual.usecases.SaveGalleryPhoto

class SavedPhotosViewModelFactory constructor(
    private val saveGalleryPhoto: SaveGalleryPhoto,
    private val deleteGalleryPhoto: DeleteGalleryPhoto,
    private val getAllStoredPhotos: GetAllStoredPhotos
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        SavedPhotosViewModel(saveGalleryPhoto, deleteGalleryPhoto, getAllStoredPhotos) as T
}