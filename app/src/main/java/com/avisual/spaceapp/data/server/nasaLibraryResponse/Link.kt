package com.avisual.spaceapp.data.server.nasaLibraryResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val href: String,
    val rel: String,
    val render: String
): Parcelable