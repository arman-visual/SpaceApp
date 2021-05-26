package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class GetGalleryPhotosByKeyword(private val galleryRepository: GalleryRepository) {

    suspend fun invoke(keyword: String): List<PhotoGallery> {
        return galleryRepository.getGalleryPhotosByKeyword(keyword)
    }
}