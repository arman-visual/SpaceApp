package com.avisual.spaceapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.PhotoGallery
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM  neo_asteroids_saved ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<Neo>>

    @Query("SELECT * FROM  neo_asteroids_saved ORDER BY id DESC")
    fun getNeoWithFlow(): Flow<List<Neo>>

    @Query("SELECT * FROM neo_asteroids_saved WHERE id = :asteroidId")
    suspend fun getById(asteroidId: String): Neo?

    @Delete
    suspend fun delete(asteroid: Neo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(asteroid: Neo)
}