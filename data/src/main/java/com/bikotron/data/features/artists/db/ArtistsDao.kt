package com.bikotron.data.features.artists.db

import androidx.room.*
import com.bikotron.data.features.releases.db.ArtistAndReleasesDb
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface ArtistsDao {

    @Query("SELECT * FROM ArtistDb order by playCount DESC")
    fun observeAll(): Observable<List<ArtistDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(artist: ArtistDb)

    @Transaction
    @Query("SELECT * FROM ArtistDb WHERE id = :id")
    fun observeByArtistId(id: String): Flowable<ArtistAndReleasesDb>
}