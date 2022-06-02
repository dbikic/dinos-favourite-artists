package com.bikotron.data.features.artists.db

import com.bikotron.core.models.ArtistJson
import com.bikotron.data.db.DinosFavouriteAlbumsDb
import com.bikotron.data.features.artists.mapper.mapToDataModel
import com.bikotron.data.features.releases.db.ArtistAndReleasesDb
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

interface ArtistsDiskDataSource {
    fun getFavouriteArtistsIdsAndListenCount(): List<Pair<String, Int>>
    fun storeArtist(artist: ArtistJson, playCount: Int)
    fun observeArtists(): Observable<List<ArtistDb>>
    fun observeArtistsAndReleases(id: String): Flowable<ArtistAndReleasesDb>
}

class ArtistsRoomDataSource @Inject constructor(
    private val db: DinosFavouriteAlbumsDb,
) : ArtistsDiskDataSource {

    override fun getFavouriteArtistsIdsAndListenCount(): List<Pair<String, Int>> {
        return listOf(
            Pair("187919", 2146),
            Pair("87016", 2021),
            Pair("56168", 1921),
            Pair("2813", 1682),
            Pair("45467", 1639),
            Pair("1014963", 1377),
            Pair("468856", 1365),
            Pair("105732", 1315),
            Pair("895602", 1261),
            Pair("257289", 1011),
        )
    }

    override fun storeArtist(artist: ArtistJson, playCount: Int) {
        return db.artistDao()
            .insert(artist.mapToDataModel(playCount))
    }

    override fun observeArtists(): Observable<List<ArtistDb>> {
        return db.artistDao()
            .observeAll()
    }

    override fun observeArtistsAndReleases(id: String): Flowable<ArtistAndReleasesDb> {
        return db.artistDao()
            .observeByArtistId(id)
    }
}