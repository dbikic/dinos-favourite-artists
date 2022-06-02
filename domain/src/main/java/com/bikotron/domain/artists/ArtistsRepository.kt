package com.bikotron.domain.artists

import com.bikotron.domain.artists.models.Artist
import com.bikotron.domain.artists.models.ArtistAndReleases
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface ArtistsRepository {
    fun observeArtists(): Observable<List<Artist>>
    fun refreshFavouriteArtists(): Completable
    fun observeArtistsAndReleases(id: String): Flowable<ArtistAndReleases>
}