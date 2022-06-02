package com.bikotron.domain.releases.usecases

import com.bikotron.domain.releases.ReleasesRepository
import io.reactivex.Completable
import javax.inject.Inject

class RefreshReleasesUseCase @Inject constructor(
    private val releasesRepository: ReleasesRepository
) {

    operator fun invoke(artistId: String): Completable {
        return releasesRepository.refreshReleases(artistId)
    }
}