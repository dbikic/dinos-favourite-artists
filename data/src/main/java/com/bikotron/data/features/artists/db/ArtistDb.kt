package com.bikotron.data.features.artists.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtistDb(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "profile") val profile: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "playCount") val playCount: Int,
)