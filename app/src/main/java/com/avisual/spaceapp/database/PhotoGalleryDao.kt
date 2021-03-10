package com.avisual.spaceapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.avisual.spaceapp.model.PhotoGallery

@Dao
interface PhotoGalleryDao {

    @Query("SELECT * FROM  photos_gallery ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<PhotoGallery>>

    @Query("SELECT * FROM photos_gallery")
    suspend fun getAll(): List<PhotoGallery>

    @Query("SELECT * FROM photos_gallery WHERE id = :id")
    suspend fun get(id: Int): PhotoGallery?

    @Query("SELECT * FROM photos_gallery WHERE nasa_id = :nasaId")
    suspend fun getByNasaID(nasaId: String): PhotoGallery

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