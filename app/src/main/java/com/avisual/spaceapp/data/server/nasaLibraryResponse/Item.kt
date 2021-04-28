package com.avisual.spaceapp.data.server.nasaLibraryResponse

import android.os.Parcelable
import com.avisual.spaceapp.data.model.PhotoGallery
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("data")
    val data_photo: List<Data>,
    val href: String,
    val links: List<Link>
) : Parcelable


fun Item.convertToPhotoGallery() = PhotoGallery(
    this.data_photo[0].nasa_id ?: "There aren't information from Nasa",
    this.href ?: "There aren't information from Nasa",
    this.links[0].href ?: "Do not exist image",
    this.data_photo[0].date_created ?: "There aren't information from Nasa",
    this.data_photo[0].description ?: "There aren't information from Nasa",
    this.data_photo[0].media_type ?: "There aren't information from Nasa",
    this.data_photo[0].photographer ?: "There aren't information from Nasa",
    this.data_photo[0].title ?: "There aren't information from Nasa"
)
