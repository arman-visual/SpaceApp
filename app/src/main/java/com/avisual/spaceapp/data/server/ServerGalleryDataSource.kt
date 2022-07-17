package com.avisual.spaceapp.data.server

import com.avisual.data.source.GalleryRemoteDataSource
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain

class ServerGalleryDataSource(private val nasaGalleryClient: NasaGalleryClient) : GalleryRemoteDataSource {
    override suspend fun findPhotosGallery(keyword: String): List<PhotoGallery> {
        return nasaGalleryClient.service.searchContain(keyword).collection.items.map { it.toGalleryDomain() }
    }
}