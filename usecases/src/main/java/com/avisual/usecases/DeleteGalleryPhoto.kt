package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class DeleteGalleryPhoto(private val galleryRepository: GalleryRepository) {

    suspend fun invoke(photoGallery: PhotoGallery) {
        galleryRepository.deletePhoto(photoGallery)
    }
}