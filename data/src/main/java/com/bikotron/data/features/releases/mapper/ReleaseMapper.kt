package com.bikotron.data.features.releases.mapper

import com.bikotron.core.models.ReleaseJson
import com.bikotron.data.features.releases.db.ReleaseDb
import com.bikotron.domain.releases.models.Release

fun ReleaseJson.mapToDataModel(artistId: String): ReleaseDb {
    return ReleaseDb(
        id = id,
        status = status,
        type = type,
        format = format,
        title = title,
        label = label,
        thumb = thumb,
        role = role,
        year = year,
        artist = artistId,
    )
}

fun ReleaseDb.mapToDomainModel(): Release {
    return Release(
        id = id,
        status = status,
        type = type,
        format = format,
        title = title,
        label = label,
        thumb = thumb,
        role = role,
        year = year,
    )
}

