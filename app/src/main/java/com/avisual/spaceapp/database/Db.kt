package com.avisual.spaceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PhotoGallery::class], version = 1)
abstract class Db : RoomDatabase() {

    abstract fun photoDao(): PhotoGalleryDao

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
