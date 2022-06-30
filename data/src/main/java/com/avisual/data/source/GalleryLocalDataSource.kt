package com.avisual.data.source

import com.avisual.domain.PhotoGallery
import kotlinx.coroutines.flow.Flow

interface GalleryLocalDataSource {

    fun getStoredPhotosInDb(): Flow<List<PhotoGallery>>?
    suspend fun getFindByNasaId(nasaId: String): PhotoGallery?
    suspend fun saveGalleryPhoto(photoGallery: PhotoGallery)
    suspend fun deletePhoto(photoGallery: PhotoGallery)
}