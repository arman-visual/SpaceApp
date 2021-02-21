package com.avisual.spaceapp.model.asteroidsNeoWsResponse

data class AsteroidsNeowsResult(
    val links: Links,
    val near_earth_objects: List<NearEarthObject>,
    val page: Page
)