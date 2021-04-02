package com.avisual.spaceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.model.PhotoGallery


@Database(entities = [PhotoGallery::class, Neo::class], version = 1)
abstract class Db : RoomDatabase() {

    abstract fun photoDao(): PhotoGalleryDao

    abstract fun asteroidDao(): AsteroidDao

    companion object {
        private var INSTANCE: Db? = null
        private const val DATABASE_NAME = "space-app.db"

        fun getDatabase(context: Context): Db {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, Db::class.java, DATABASE_NAME
                ).build()
            }
            return INSTANCE!!
        }
    }
}
