package com.bikotron.data.features.releases.db

import com.bikotron.core.models.ReleaseJson
import com.bikotron.data.db.DinosFavouriteAlbumsDb
import com.bikotron.data.features.releases.mapper.mapToDataModel
import javax.inject.Inject

interface ReleasesDiskDataSource {
    fun storeReleases(artistId: String, releases: List<ReleaseJson>)
}

class ReleasesRoomDataSource @Inject constructor(
    private val db: DinosFavouriteAlbumsDb,
) : ReleasesDiskDataSource {

    override fun storeReleases(artistId: String, releases: List<ReleaseJson>) {
        return db.releasesDao()
            .insertOrUpdate(releases.map { it.mapToDataModel(artistId) })
    }
}