package com.bikotron.domain.releases

import io.reactivex.Completable

interface ReleasesRepository {
    fun refreshReleases(artistId: String): Completable
}