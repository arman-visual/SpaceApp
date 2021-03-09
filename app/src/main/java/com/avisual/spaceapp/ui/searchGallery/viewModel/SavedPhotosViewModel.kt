package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    private val _savedPhotos = MutableLiveData<List<PhotoGallery>>(emptyList())
    val savedPhotos: LiveData<List<PhotoGallery>>
        get() = _savedPhotos

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() {
        launch {
            _savedPhotos.value = photoGalleryRepository.getAllPhotos()
        }
    }
}