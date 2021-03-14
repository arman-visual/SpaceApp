package com.avisual.spaceapp.model.nasaLibraryResponse

import android.os.Parcelable
import com.avisual.spaceapp.model.PhotoGallery
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
    this.data_photo[0].nasa_id ?: "Do not exist nasa id information",
    this.links[0].href ?: "Do not exist image",
    this.data_photo[0].date_created ?: "Do not exist date of created information",
    this.data_photo[0].description ?: "Do not exist description information",
    this.data_photo[0].media_type ?: "Do not exist media type information",
    this.data_photo[0].photographer ?: "Do not exist photographer information",
    this.data_photo[0].title ?: "Do not exist title information",
    false
)
