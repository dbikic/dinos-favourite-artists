package com.bikotron.data.features.artists

import com.bikotron.data.features.artists.db.ArtistsDiskDataSource
import com.bikotron.data.features.artists.mapper.mapToDomainModel
import com.bikotron.data.features.artists.network.ArtistsNetworkDataSource
import com.bikotron.domain.artists.ArtistsRepository
import com.bikotron.domain.artists.models.Artist
import com.bikotron.domain.artists.models.ArtistAndReleases
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ArtistsRepositoryProvider @Inject constructor(
    private val networkDataSource: ArtistsNetworkDataSource,
    private val diskDataSource: ArtistsDiskDataSource,
) : ArtistsRepository {

    override fun refreshFavouriteArtists(): Completable {
        return Completable.merge(
            diskDataSource.getFavouriteArtistsIdsAndListenCount().map { idAndListenCount ->
                networkDataSource.fetchArtist(idAndListenCount.first)
                    .doOnSuccess { artistJson ->
                        diskDataSource.storeArtist(artistJson, idAndListenCount.second)
                    }
                    .ignoreElement()
            }
        )
    }

    override fun observeArtists(): Observable<List<Artist>> {
        return diskDataSource.observeArtists()
            .map { artists ->
                artists.map { artist ->
                    artist.mapToDomainModel()
                }
            }
    }

    override fun observeArtistsAndReleases(id: String): Flowable<ArtistAndReleases> {
        return diskDataSource.observeArtistsAndReleases(id)
            .map {
                it.mapToDomainModel()
            }
    }
}