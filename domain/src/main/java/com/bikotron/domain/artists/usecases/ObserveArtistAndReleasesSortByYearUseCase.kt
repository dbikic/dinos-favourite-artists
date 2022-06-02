package com.bikotron.domain.artists.usecases

import com.bikotron.domain.artists.ArtistsRepository
import com.bikotron.domain.artists.models.ArtistAndReleases
import io.reactivex.Flowable
import javax.inject.Inject

class ObserveArtistAndReleasesSortByYearUseCase @Inject constructor(
    private val artistsRepository: ArtistsRepository
) {

    operator fun invoke(artistId: String): Flowable<ArtistAndReleases> {
        return artistsRepository.observeArtistsAndReleases(artistId)
            .map {
                ArtistAndReleases(
                    it.artist,
                    it.releases.sortedBy { release ->
                        release.year
                    }
                )
            }
    }
}