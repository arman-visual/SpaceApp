package com.avisual.spaceapp.model.asteroidsNeoWsResponse

data class CloseApproachData(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Int,
    val miss_distance: MissDistance,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocity
)