package com.bikotron.core.models

import com.google.gson.annotations.SerializedName

data class ReleasesJson(
    @SerializedName("pagination") val pagination: PaginationJson,
    @SerializedName("releases") val releases: List<ReleaseJson>
)

data class ReleaseJson(
    @SerializedName("id") val id: String,
    @SerializedName("status") val status: String?,
    @SerializedName("type") val type: String,
    @SerializedName("format") val format: String?,
    @SerializedName("label") val label: String?,
    @SerializedName("title") val title: String,
    @SerializedName("thumb") val thumb: String,
    @SerializedName("role") val role: String,
    @SerializedName("year") val year: Int,
)