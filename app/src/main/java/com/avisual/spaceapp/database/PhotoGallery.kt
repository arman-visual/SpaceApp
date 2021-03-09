package com.avisual.spaceapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_gallery")
data class PhotoGallery (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var url:String,
    var date_created: String,
    var description: String,
    var media_type: String,
    var nasa_id: String,
    var photographer: String,
    var title: String
)