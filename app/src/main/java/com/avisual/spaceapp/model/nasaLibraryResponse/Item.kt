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
    this.links[0].href?:"Don't exist image",
    this.data_photo[0].date_created?:"Don't exist date of created information",
    this.data_photo[0].description?:"Don't exist description information",
    this.data_photo[0].media_type?:"Don't exist media type information",
    this.data_photo[0].nasa_id?:"Don't exist nasa id information",
    this.data_photo[0].photographer?:"Don't exist photographer information" ,
    this.data_photo[0].title?:"Don't exist title information"
)
