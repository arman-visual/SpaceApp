package com.avisual.spaceapp.database

import com.avisual.data.source.GalleryLocalDataSource
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.server.toGalleryDomain
import com.avisual.spaceapp.server.toGalleryFramework
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomGalleryDataSource(private val db: Db) : GalleryLocalDataSource {

    private val galleryDao = db.photoDao()

    override fun getStoredPhotosInDb(): Flow<List<PhotoGallery>> {
        return galleryDao.getStoredPhotos().map { listFramework ->
            listFramework.map { frameworkGallery -> frameworkGallery.toGalleryDomain() }
        }
    }

    override suspend fun getFindByNasaId(nasaId: String): PhotoGallery? =
        withContext(Dispatchers.IO) {
            galleryDao.getByNasaId(nasaId)?.toGalleryDomain()
        }

    override suspend fun saveGalleryPhoto(photoGallery: PhotoGallery) {
        withContext(Dispatchers.IO) {
            galleryDao.insert(photoGallery.toGalleryFramework())
        }
    }

    override suspend fun deletePhoto(photoGallery: PhotoGallery) {
        withContext(Dispatchers.IO) {
            galleryDao.delete(photoGallery.toGalleryFramework())
        }
    }
}