package com.avisual.spaceapp.data.server.nasaLibraryResponse

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("data")
    val data_photo: List<Data> = emptyList(),
    val href: String = "",
    val links: List<Link> = emptyList()
)