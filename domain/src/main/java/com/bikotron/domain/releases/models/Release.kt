package com.bikotron.domain.releases.models

data class Release(
    val id: String,
    val status: String?,
    val type: String,
    val format: String?,
    val title: String,
    val label: String?,
    val thumb: String,
    val role: String,
    val year: Int,
)