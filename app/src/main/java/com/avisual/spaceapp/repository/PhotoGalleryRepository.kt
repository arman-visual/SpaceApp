package com.avisual.spaceapp.repository

import com.avisual.spaceapp.model.nasaLibraryResponse.CollectionNasaResult
import com.avisual.spaceapp.server.NasaClient

class PhotoGalleryRepository {

    suspend fun findPhotosGallery(keyword: String): CollectionNasaResult {
        return NasaClient.libraryService.searchContain(keyword)
    }
}