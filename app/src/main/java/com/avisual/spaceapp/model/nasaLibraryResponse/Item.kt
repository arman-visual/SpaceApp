package com.avisual.spaceapp.model.nasaLibraryResponse

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("data")
    val data_photo: List<Data>,
    val href: String,
    val links: List<Link>
)