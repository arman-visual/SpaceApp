package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class SaveGalleryPhotoUseCase(private val galleryRepository: GalleryRepository) {

    suspend operator fun  invoke(photoGallery: PhotoGallery){
        galleryRepository.savePhoto(photoGallery)
    }
}