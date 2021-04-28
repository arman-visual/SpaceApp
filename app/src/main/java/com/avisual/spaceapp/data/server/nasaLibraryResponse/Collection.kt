package com.avisual.spaceapp.data.server.nasaLibraryResponse

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)