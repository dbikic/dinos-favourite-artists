package com.bikotron.data.features.artists.mapper

import com.bikotron.data.features.releases.db.ArtistAndReleasesDb
import com.bikotron.data.features.releases.mapper.mapToDomainModel
import com.bikotron.domain.artists.models.ArtistAndReleases

fun ArtistAndReleasesDb.mapToDomainModel(): ArtistAndReleases {
    return ArtistAndReleases(
        artist = artist.mapToDomainModel(),
        releases = releases.map { it.mapToDomainModel() }.sortedBy { it.year }
    )
}

