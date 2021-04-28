package com.avisual.data.repository

import com.avisual.data.source.GalleryRemoteDataSource
import com.avisual.domain.PhotoGallery

class GalleryRepository(private val galleryRemoteDataSource: GalleryRemoteDataSource) {

    suspend fun getGalleryPhotosByKeyword(keyword: String): List<PhotoGallery> {
        return galleryRemoteDataSource.findPhotosGallery(keyword)
    }
}