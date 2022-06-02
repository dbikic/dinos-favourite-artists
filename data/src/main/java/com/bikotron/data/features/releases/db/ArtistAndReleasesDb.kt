package com.bikotron.data.features.releases.db

import androidx.room.Embedded
import androidx.room.Relation
import com.bikotron.data.features.artists.db.ArtistDb

data class ArtistAndReleasesDb(
    @Embedded
    val artist: ArtistDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "artist"
    )
    val releases: List<ReleaseDb>
)