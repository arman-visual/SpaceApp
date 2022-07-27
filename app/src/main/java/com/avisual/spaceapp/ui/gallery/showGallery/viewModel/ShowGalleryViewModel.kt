package com.avisual.spaceapp.ui.gallery.showGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avisual.domain.PhotoGallery
import com.avisual.usecases.GetGalleryPhotosByKeyword
import kotlinx.coroutines.launch

class ShowGalleryViewModel(private val getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword) :
    ViewModel() {

    companion object {
        private const val DEFAULT_KEYWORD = "Nasa"
    }

    private val _model = MutableLiveData<GalleryUi>()
    val model: LiveData<GalleryUi>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private fun refresh() = viewModelScope.launch {
        _model.value = GalleryUi.Loading

        val response = getGalleryPhotosByKeyword.invoke(DEFAULT_KEYWORD)

        response?.let {
            _model.value =
                GalleryUi.Content(it)
        }?:run {
            _model.value = GalleryUi.Content(emptyList())
        }
    }

    fun findPhotosByKeyword(keyword: String) {
        viewModelScope.launch {
            _model.value = GalleryUi.Loading

            val response = getGalleryPhotosByKeyword.invoke(keyword)
            response?.let {
                _model.value =
                    GalleryUi.Content(it)
            }?:run {
                _model.value = GalleryUi.Content(emptyList())
            }
        }
    }

    sealed class GalleryUi {
        object Loading : GalleryUi()
        data class Content(val photos: List<PhotoGallery>? = null) : GalleryUi()
    }
}

