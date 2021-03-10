package com.avisual.spaceapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photos_gallery")
@Parcelize
data class PhotoGallery(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var url: String,
    var date_created: String,
    var description: String,
    var media_type: String,
    var nasa_id: String,
    var photographer: String,
    var title: String
) : Parcelable