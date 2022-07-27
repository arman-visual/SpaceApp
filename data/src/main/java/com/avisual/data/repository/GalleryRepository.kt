package com.avisual.data.repository

import com.avisual.data.source.GalleryLocalDataSource
import com.avisual.data.source.GalleryRemoteDataSource
import com.avisual.domain.PhotoGallery
import kotlinx.coroutines.flow.Flow

class GalleryRepository(
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    private val galleryLocalDataSource: GalleryLocalDataSource
) {

    suspend fun getGalleryPhotosByKeyword(keyword: String): List<PhotoGallery> {
        return galleryRemoteDataSource.findPhotosGallery(keyword)
    }

    fun getAllStoredPhotos(): Flow<List<PhotoGallery>>? {
        return galleryLocalDataSource.getStoredPhotosInDb()
    }

    suspend fun getPhotoById(id: String): PhotoGallery? {
        return galleryLocalDataSource.getFindByNasaId(id)
    }

    suspend fun savePhoto(photoGallery: PhotoGallery){
        galleryLocalDataSource.saveGalleryPhoto(photoGallery)
    }

    suspend fun deletePhoto(photoGallery: PhotoGallery){
        galleryLocalDataSource.deletePhoto(photoGallery)
    }
}