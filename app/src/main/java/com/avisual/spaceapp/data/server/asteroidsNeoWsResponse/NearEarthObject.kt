package com.avisual.spaceapp.data.server.asteroidsNeoWsResponse

import com.google.gson.annotations.SerializedName

data class NearEarthObjectResult(
    val links: LinksHeader,

    @SerializedName("element_count")
    val elementCount: Long,

    @SerializedName("near_earth_objects")
    val registerDay: Map<String, List<NearEarthObject>>

)

data class NearEarthObject(
    val links: NearEarthObjectLinks,
    val id: String,

    @SerializedName("neo_reference_id")
    val neoReferenceID: String,

    val name: String,

    @SerializedName("nasa_jpl_url")
    val nasaJplURL: String,

    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitudeH: Double,

    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,

    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,

    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachDatum>,

    @SerializedName("is_sentry_object")
    val isSentryObject: Boolean
)

data class EstimatedDiameter(
    val kilometers: Feet,
    val meters: Feet,
    val miles: Feet,
    val feet: Feet
)

data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: String,

    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: String,

    @SerializedName("miles_per_hour")
    val milesPerHour: String
)

data class MissDistance(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

data class Feet(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double,

    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double
)

data class NearEarthObjectLinks(
    val self: String
)

data class LinksHeader(
    val next: String,
    val prev: String,
    val self: String
)