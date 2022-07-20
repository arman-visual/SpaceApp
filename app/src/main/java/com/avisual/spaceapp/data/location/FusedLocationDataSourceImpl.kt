package com.avisual.spaceapp.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.avisual.data.source.LocationDataSource
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FusedLocationDataSourceImpl(
    private val context: Context
) :
    LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): String =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener { location ->
                val geocoder = Geocoder(context)
                val result =
                    geocoder.getFromLocation(location.result.latitude, location.result.longitude, 1)
                        .first().countryCode ?: "US"
                continuation.resume(result)
            }
        }
}