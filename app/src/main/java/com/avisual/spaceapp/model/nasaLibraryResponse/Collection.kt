package com.avisual.spaceapp.model.nasaLibraryResponse

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)