package com.avisual.spaceapp.data.database

import com.avisual.data.source.GalleryLocalDataSource
import com.avisual.domain.PhotoGallery
import com.avisual.spaceapp.data.toGalleryDomain
import com.avisual.spaceapp.data.toGalleryFramework
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomGalleryDataSource(db: Db) : GalleryLocalDataSource {

    private val galleryDao = db.photoDao()

    override fun getStoredPhotosInDb(): Flow<List<PhotoGallery>>? {
        return galleryDao.getStoredPhotos()?.let { flow ->
            flow.map { listPhotosRoom ->
                listPhotosRoom.map { it.toGalleryDomain() }
            }
        } ?: run {
            null
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