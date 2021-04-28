package com.avisual.spaceapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photos_curiosity")
@Parcelize
data class PhotoRover(
    @PrimaryKey
    val id: Int,
    val img_src: String,
    val full_name: String,
    val camera_name: String,
    val rover_id: Int,
    val earth_date: String,
    val landing_date: String,
    val launch_date: String,
    val rover_name: String,
    val status: String,
    val sol: Int
) : Parcelable
