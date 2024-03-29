package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery

class GetGalleryPhotosByKeywordUseCase(private val galleryRepository: GalleryRepository) {

    suspend operator fun  invoke(keyword: String): List<PhotoGallery> {
        return galleryRepository.getGalleryPhotosByKeyword(keyword)
    }
}