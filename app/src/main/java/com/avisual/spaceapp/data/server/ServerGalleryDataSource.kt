package com.avisual.spaceapp.data.server

import android.util.Log
import com.avisual.data.source.GalleryRemoteDataSource
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain

class ServerGalleryDataSource(private val nasaGalleryClient: NasaGalleryClient) :
    GalleryRemoteDataSource {
    override suspend fun findPhotosGallery(keyword: String): List<PhotoGallery> {
        return if (nasaGalleryClient.service.searchContain(keyword).isSuccessful)
            nasaGalleryClient.service.searchContain(keyword)
                .body()!!.collection.items.map { it.toGalleryDomain() }
        else {
            val errorBody = nasaGalleryClient.service.searchContain(keyword).errorBody()
            Log.e("ERROR", errorBody.toString())
            emptyList()
        }
    }
}