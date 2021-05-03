package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class GetGalleryPhotoById(private val galleryRepository: GalleryRepository) {
    suspend fun invoke(id: String): PhotoGallery? {
        return galleryRepository.getPhotoById(id)
    }
}