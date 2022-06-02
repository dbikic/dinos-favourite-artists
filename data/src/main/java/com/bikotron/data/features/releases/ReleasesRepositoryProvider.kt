package com.bikotron.data.features.releases

import com.bikotron.data.features.releases.db.ReleasesDiskDataSource
import com.bikotron.data.features.releases.network.ReleasesNetworkDataSource
import com.bikotron.domain.releases.ReleasesRepository
import io.reactivex.Completable
import javax.inject.Inject

class ReleasesRepositoryProvider @Inject constructor(
    private val networkDataSource: ReleasesNetworkDataSource,
    private val diskDataSource: ReleasesDiskDataSource,
) : ReleasesRepository {

    override fun refreshReleases(artistId: String): Completable {
        return networkDataSource.fetchReleases(artistId)
            .doOnNext {
                diskDataSource.storeReleases(artistId, it.releases)
            }
            .ignoreElements()
    }
}