package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.spaceapp.ui.common.ScopeViewModel
import com.avisual.usecases.GetGalleryPhotosByKeyword
import kotlinx.coroutines.launch

class ShowGalleryViewModel(private val getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword) :
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

        val response = getGalleryPhotosByKeyword.invoke(DEFAULT_KEYWORD)
            .map { galleryDomain -> galleryDomain.toGalleryFramework() }
        _photos.value =
            GalleryUi.Content(response)
    }

    fun findPhotosByKeyword(keyword: String) {
        launch {
            _photos.value = GalleryUi.Loading

            val response = getGalleryPhotosByKeyword.invoke(keyword)
                .map { galleryDomain -> galleryDomain.toGalleryFramework() }
            _photos.value =
                GalleryUi.Content(response)
        }
    }
}

sealed class GalleryUi {
    object Loading : GalleryUi()
    class Content(val photos: List<PhotoGallery>) : GalleryUi()
}