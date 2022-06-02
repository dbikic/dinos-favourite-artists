package com.bikotron.core.models

import com.google.gson.annotations.SerializedName

data class PaginationJson(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
)