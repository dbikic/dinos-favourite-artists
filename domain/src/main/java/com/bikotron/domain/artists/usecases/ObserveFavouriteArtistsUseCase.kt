package com.bikotron.domain.artists.usecases

import com.bikotron.domain.artists.ArtistsRepository
import com.bikotron.domain.artists.models.Artist
import io.reactivex.Observable
import javax.inject.Inject

class ObserveFavouriteArtistsUseCase @Inject constructor(
    private val artistsRepository: ArtistsRepository
) {

    operator fun invoke(): Observable<out List<Artist>> {
        return artistsRepository.observeArtists()
    }
}