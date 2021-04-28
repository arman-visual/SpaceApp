package com.avisual.spaceapp.server

import com.avisual.data.source.GalleryRemoteDataSource
import com.avisual.domain.PhotoGallery

class ServerGalleryDataSource : GalleryRemoteDataSource {
    override suspend fun findPhotosGallery(keyword: String): List<PhotoGallery> {
        return NasaGalleryClient.service.searchContain(keyword).collection.items.map { it.toGalleryDomain() }
    }
}