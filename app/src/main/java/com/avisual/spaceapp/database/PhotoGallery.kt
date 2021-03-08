package com.avisual.spaceapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_gallery")
data class PhotoGallery (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url:String,
    val center: String,
    val date_created: String,
    val description: String,
    val description_508: String,
    val location: String,
    val media_type: String,
    val nasa_id: String,
    val photographer: String,
    val secondary_creator: String,
    val title: String
)