package com.avisual.spaceapp.data.server.asteroidsNeoWsResponse

import com.google.gson.annotations.SerializedName

data class CloseApproachDatum(
    @SerializedName("close_approach_date")
    val closeApproachDate: String,

    @SerializedName("close_approach_date_full")
    val closeApproachDateFull: String,

    @SerializedName("epoch_date_close_approach")
    val epochDateCloseApproach: Long,

    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity,

    @SerializedName("miss_distance")
    val missDistance: MissDistance,

    @SerializedName("orbiting_body")
    val orbitingBody: String
)

