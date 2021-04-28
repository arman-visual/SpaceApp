package com.avisual.spaceapp.ui.searchGallery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisual.spaceapp.ui.common.ScopeViewModel
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.server.toGalleryDomain
import com.avisual.spaceapp.data.server.toGalleryFramework
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetAllStoredPhotos
import com.avisual.usecases.SaveGalleryPhoto
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedPhotosViewModel(
    private val saveGalleryPhoto: SaveGalleryPhoto,
    private val deleteGalleryPhoto: DeleteGalleryPhoto,
    private val getAllStoredPhotos: GetAllStoredPhotos
) :
    ScopeViewModel() {

    private val _storedPhotos = MutableLiveData<List<PhotoGallery>>()
    val storedPhotos: LiveData<List<PhotoGallery>> get() = _storedPhotos

    init {
        startCollectingPhotos()
    }

    private fun startCollectingPhotos() {
        launch {
            getAllStoredPhotos.invoke().collect { listPhotoGalleryDomain ->
                _storedPhotos.value = listPhotoGalleryDomain.map {
                    it.toGalleryFramework()
                }
            }
        }

    }

    fun deletePhoto(photoGallery: PhotoGallery) {
        launch {
            deleteGalleryPhoto.invoke(photoGallery.toGalleryDomain())
        }
    }

    fun savePhoto(photoGallery: PhotoGallery) {
        launch {
            saveGalleryPhoto.invoke(photoGallery.toGalleryDomain())
        }
    }
}