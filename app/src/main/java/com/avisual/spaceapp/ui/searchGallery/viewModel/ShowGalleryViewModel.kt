package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class ShowGalleryViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    companion object {
        private const val DEFAULT_KEYWORD = "Nasa"
    }

    private val _photosLibrary = MutableLiveData<List<Item>>()
    val photosLibrary: LiveData<List<Item>>
        get() = _photosLibrary

    init {
        refresh()
    }

    private fun refresh() = launch {
        val response = photoGalleryRepository.findPhotosGallery(DEFAULT_KEYWORD)
        _photosLibrary.value = response.collection.items
    }

    fun findPhotosByKeyword(keyword: String) {
        launch {
            val searchResponse = photoGalleryRepository.findPhotosGallery(keyword)
            _photosLibrary.value = searchResponse.collection.items
        }
    }
}

