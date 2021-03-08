package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val photoGalleryRepository: PhotoGalleryRepository) :
    ScopeViewModel() {

   // private val _photosFavorites = MutableLiveData<List<PhotoGallery>>()
    //val photosFavorites: LiveData<List<PhotoGallery>>
     //   get() = _photosLibrary

    fun getAllFavorites(photo: PhotoGallery) {
        launch {
            var photosBD = photoGalleryRepository.getAllPhotos()
          //  _photosFavorites.value = photosBD.value
            //TODO conectar VM con Vista e imprimir datos de BD y consumir tambien.
        }
    }
}