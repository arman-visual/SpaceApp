package com.avisual.spaceapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.avisual.spaceapp.model.Neo

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM  neo_asteroids_saved ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<Neo>>

    @Delete
    suspend fun delete(asteroid: Neo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(asteroid: Neo)
}