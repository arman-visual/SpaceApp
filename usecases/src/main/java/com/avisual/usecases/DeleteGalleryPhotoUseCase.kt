package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class DeleteGalleryPhotoUseCase(private val galleryRepository: GalleryRepository) {

    suspend operator fun invoke(photoGallery: PhotoGallery) {
        galleryRepository.deletePhoto(photoGallery)
    }
}