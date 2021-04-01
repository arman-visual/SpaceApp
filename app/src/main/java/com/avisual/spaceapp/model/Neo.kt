package com.avisual.spaceapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Neo(
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
