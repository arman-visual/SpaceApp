package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.model.nasaLibraryResponse.convertToPhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class ShowGalleryViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

    companion object {
        private const val DEFAULT_KEYWORD = "Nasa"
    }

    private val _photos = MutableLiveData<GalleryUi>()
    val photos: LiveData<GalleryUi>
        get() = _photos

    init {
        refresh()
    }

    private fun refresh() = launch {
        _photos.value = GalleryUi.Loading

        val response = photoGalleryRepository.findPhotosGallery(DEFAULT_KEYWORD)
        _photos.value =
            GalleryUi.Content(response.collection.items.map { it.convertToPhotoGallery() })
    }

    fun findPhotosByKeyword(keyword: String) {
        launch {
            _photos.value = GalleryUi.Loading

            val searchResponse = photoGalleryRepository.findPhotosGallery(keyword)
            _photos.value =
                GalleryUi.Content(searchResponse.collection.items.map { it.convertToPhotoGallery() })
        }
    }
}

sealed class GalleryUi {
    object Loading : GalleryUi()
    class Content(val photos: List<PhotoGallery>) : GalleryUi()
}