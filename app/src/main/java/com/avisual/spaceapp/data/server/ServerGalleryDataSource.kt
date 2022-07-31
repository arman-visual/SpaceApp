package com.avisual.spaceapp.data.server

import android.util.Log
import com.avisual.data.source.GalleryRemoteDataSource
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain
import retrofit2.HttpException
import java.io.IOException

class ServerGalleryDataSource(private val nasaGalleryClient: NasaGalleryClient) :
    GalleryRemoteDataSource {
    override suspend fun findPhotosGallery(keyword: String): List<PhotoGallery> {
        return try {

            val response = nasaGalleryClient.service.searchContain(keyword)
            response.body()?.collection?.items?.map {
                it.toGalleryDomain()
            } ?: emptyList()

        } catch (exception: IOException) {
            Log.e("ServerRoverDataSource", "IOException, check internet connection")
            emptyList()
        } catch (exception: HttpException) {
            Log.e("ServerRoverDataSource", exception.message())
            emptyList()
        }
    }
}