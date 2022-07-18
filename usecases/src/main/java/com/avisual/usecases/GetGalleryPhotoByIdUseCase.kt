package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class GetGalleryPhotoByIdUseCase(private val galleryRepository: GalleryRepository) {
    suspend operator fun  invoke(id: String): PhotoGallery? {
        return galleryRepository.getPhotoById(id)
    }
}