package com.bikotron.domain.artists.models

import com.bikotron.domain.releases.models.Release

data class ArtistAndReleases(
    val artist: Artist,
    val releases: List<Release>,
)