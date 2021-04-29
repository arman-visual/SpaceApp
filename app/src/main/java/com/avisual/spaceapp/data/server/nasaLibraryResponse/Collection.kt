package com.avisual.spaceapp.data.server.nasaLibraryResponse

data class Collection(
    val href: String = "",
    val items: List<Item> = emptyList(),
    val links: List<LinkX> = emptyList(),
    val metadata: Metadata,
    val version: String = ""
)