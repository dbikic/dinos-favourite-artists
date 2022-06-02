package com.bikotron.domain.artists.models

data class Artist(
    val id: String,
    val name: String,
    val profile: String,
    val image: String?,
    val playCount: Int,
)