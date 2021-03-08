package com.avisual.spaceapp.repository

import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.database.PhotoGalleryDao
import com.avisual.spaceapp.model.nasaLibraryResponse.CollectionNasaResult
import com.avisual.spaceapp.server.NasaClient

class PhotoGalleryRepository(database: Db) {

    private var photoGalleryDao: PhotoGalleryDao = database.photoDao()

    suspend fun findPhotosGallery(keyword: String): CollectionNasaResult {
        return NasaClient.libraryService.searchContain(keyword)
    }

    suspend fun saveFavoritePhoto(photoGallery: PhotoGallery) {
        photoGalleryDao.insert(photoGallery)
    }
}