package com.avisual.spaceapp.model.nasaLibraryResponse

import android.os.Parcelable
import com.avisual.spaceapp.database.PhotoGallery
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
    0,
    this.links[0].href,
    this.data_photo[0].date_created,
    this.data_photo[0].description,
    this.data_photo[0].media_type,
    this.data_photo[0].nasa_id,
    this.data_photo[0].photographer,
    this.data_photo[0].title
)
