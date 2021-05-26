package com.avisual.spaceapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "neo_asteroids_saved")
@Parcelize
data class Neo(
    @PrimaryKey
    var id: String,
    val name: String,
    val isPotentiallyHazardousAsteroid: Boolean,
    val absoluteMagnitudeH: Double,
    val nasaJplURL: String,
    val minDiameter:Double,
    val maxDiameter:Double,
    val relativeVelocitySeconds:String,
    val relativeVelocityHour:String,
    val approachDate:String,
    val approachDateFull:String,
    val missDistance: String,
    val dayRegister: String
):Parcelable
