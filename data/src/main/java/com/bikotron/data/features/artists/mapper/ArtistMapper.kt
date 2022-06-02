package com.bikotron.data.features.artists.mapper

import com.bikotron.core.models.ArtistJson
import com.bikotron.data.features.artists.db.ArtistDb
import com.bikotron.domain.artists.models.Artist

fun ArtistJson.mapToDataModel(playCount: Int): ArtistDb {
    return ArtistDb(
        id = id,
        name = name,
        profile = profile,
        image = images.firstOrNull()?.uri ?: "",
        playCount = playCount,
    )
}

fun ArtistDb.mapToDomainModel(): Artist {
    return Artist(
        id = id,
        name = name,
        profile = profile,
        image = image,
        playCount = playCount,
    )
}

