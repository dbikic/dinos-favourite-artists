package com.bikotron.core.models

import com.google.gson.annotations.SerializedName

data class ArtistJson(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile") val profile: String,
    @SerializedName("images") val images: List<JsonImage>,
)

data class JsonImage(
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
)