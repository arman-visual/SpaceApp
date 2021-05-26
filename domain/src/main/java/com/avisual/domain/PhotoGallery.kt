package com.avisual.domain

data class PhotoGallery(
    var nasa_id: String,
    var jsonAllSized: String,
    var url: String,
    var date_created: String,
    var description: String,
    var media_type: String,
    var photographer: String,
    var title: String
)