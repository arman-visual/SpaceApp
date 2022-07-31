package com.avisual.usecases

import com.avisual.data.repository.GalleryRepository
import com.avisual.domain.PhotoGallery
import kotlinx.coroutines.flow.Flow

class GetAllStoredPhotosUseCase(private val galleryRepository: GalleryRepository) {

    fun invoke(): Flow<List<PhotoGallery>>? {
        return galleryRepository.getAllStoredPhotos()
    }
}