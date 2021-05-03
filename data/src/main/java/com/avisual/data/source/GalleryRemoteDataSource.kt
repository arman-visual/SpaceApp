package com.avisual.data.source

import com.avisual.domain.PhotoGallery

interface GalleryRemoteDataSource {

    suspend fun findPhotosGallery(keyword: String): List<PhotoGallery>
}