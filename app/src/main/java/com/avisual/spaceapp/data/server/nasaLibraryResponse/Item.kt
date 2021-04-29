package com.avisual.spaceapp.data.server.nasaLibraryResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("data")
    val data_photo: List<Data> = emptyList(),
    val href: String = "",
    val links: List<Link> = emptyList()
) : Parcelable