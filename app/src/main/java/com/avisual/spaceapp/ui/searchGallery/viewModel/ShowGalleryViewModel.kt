package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.model.nasaLibraryResponse.convertToPhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class ShowGalleryViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    companion object {
        private const val DEFAULT_KEYWORD = "Nasa"
    }

    private val _photosLibrary = MutableLiveData<List<PhotoGallery>>()
    val photosLibrary: LiveData<List<PhotoGallery>>
        get() = _photosLibrary

    init {
        refresh()
    }

    private fun refresh() = launch {
        val response = photoGalleryRepository.findPhotosGallery(DEFAULT_KEYWORD)
        _photosLibrary.value = response.collection.items.map { it.convertToPhotoGallery() }
    }

    fun findPhotosByKeyword(keyword: String) {
        launch {
            val searchResponse = photoGalleryRepository.findPhotosGallery(keyword)
            _photosLibrary.value =
                searchResponse.collection.items.map { it.convertToPhotoGallery() }
        }
    }
}

