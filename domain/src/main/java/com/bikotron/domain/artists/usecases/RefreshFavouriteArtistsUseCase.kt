package com.bikotron.domain.artists.usecases

import com.bikotron.domain.artists.ArtistsRepository
import io.reactivex.Completable
import javax.inject.Inject

class RefreshFavouriteArtistsUseCase @Inject constructor(
    private val artistsRepository: ArtistsRepository
) {

    operator fun invoke(): Completable {
        return artistsRepository.refreshFavouriteArtists()
    }
}