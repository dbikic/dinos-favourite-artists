package com.bikotron.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bikotron.data.features.artists.db.ArtistsDao
import com.bikotron.data.features.artists.db.ArtistDb
import com.bikotron.data.features.releases.db.ReleaseDb
import com.bikotron.data.features.releases.db.ReleasesDao

@Database(
    entities = [
        ArtistDb::class,
        ReleaseDb::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class DinosFavouriteAlbumsDb : RoomDatabase() {
    abstract fun artistDao(): ArtistsDao
    abstract fun releasesDao(): ReleasesDao
}