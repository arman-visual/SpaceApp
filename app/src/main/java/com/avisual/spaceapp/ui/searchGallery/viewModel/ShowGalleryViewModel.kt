package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.server.NasaClient
import kotlinx.coroutines.launch

class ShowGalleryViewModel(private val photoGalleryRepository: PhotoGalleryRepository) : ScopeViewModel() {

    private val _photosLibrary = MutableLiveData<List<Item>>()
    val photosLibrary: LiveData<List<Item>>
        get() = _photosLibrary

    init {
        refresh()
    }

    private fun refresh() = launch {
       // var response = NasaClient.libraryService.searchContain("Neptune")
        var response = photoGalleryRepository.findPhotosGallery("Neptune")
        _photosLibrary.value = response.collection.items
    }
}

