package com.avisual.spaceapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photos_gallery")
@Parcelize
data class PhotoGallery(
    @PrimaryKey
    var nasa_id: String,
    var url: String,
    var date_created: String,
    var description: String,
    var media_type: String,
    var photographer: String,
    var title: String,
    var isFavorite:Boolean
) : Parcelable