package com.avisual.spaceapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.avisual.spaceapp.data.model.PhotoGallery

@Dao
interface PhotoGalleryDao {

    @Query("SELECT * FROM  photos_gallery ORDER BY nasa_id DESC")
    fun getAllLiveData(): LiveData<List<PhotoGallery>>

    @Query("SELECT * FROM  photos_gallery ORDER BY nasa_id DESC")
    fun getStoredPhotos(): Flow<List<PhotoGallery>>

    @Query("SELECT * FROM photos_gallery")
    suspend fun getAll(): List<PhotoGallery>

    @Query("SELECT * FROM photos_gallery WHERE nasa_id = :nasaId")
    suspend fun getByNasaId(nasaId: String): PhotoGallery?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<PhotoGallery>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photo: PhotoGallery)

    @Update
    suspend fun update(photo: PhotoGallery)

    @Delete
    suspend fun delete(photo: PhotoGallery)

    @Query("DELETE FROM photos_gallery")
    suspend fun deleteAll()
}