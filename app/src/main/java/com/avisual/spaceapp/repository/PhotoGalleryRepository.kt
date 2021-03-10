package com.avisual.spaceapp.repository

import androidx.lifecycle.LiveData
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.database.PhotoGalleryDao
import com.avisual.spaceapp.model.nasaLibraryResponse.CollectionNasaResult
import com.avisual.spaceapp.server.NasaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoGalleryRepository(database: Db) {

    private var photoGalleryDao: PhotoGalleryDao = database.photoDao()

    suspend fun findPhotosGallery(keyword: String): CollectionNasaResult {
        return NasaClient.libraryService.searchContain(keyword)
    }

    suspend fun savePhoto(photoGallery: PhotoGallery) = withContext(Dispatchers.IO) {
        photoGalleryDao.insert(photoGallery)
    }

    suspend fun getAllPhotos(): List<PhotoGallery> = withContext(Dispatchers.IO) {
        photoGalleryDao.getAll()
    }

    fun getAllPhotosLiveData(): LiveData<List<PhotoGallery>> {
        return photoGalleryDao.getAllLiveData()
    }

    suspend fun getFindById(nasaId: String): PhotoGallery = withContext(Dispatchers.IO) {
        photoGalleryDao.getByNasaID(nasaId)
    }
}