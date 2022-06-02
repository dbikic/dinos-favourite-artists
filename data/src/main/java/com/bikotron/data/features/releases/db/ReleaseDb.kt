package com.bikotron.data.features.releases.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bikotron.data.features.artists.db.ArtistDb

@Entity(
    foreignKeys = [ForeignKey(
        entity = ArtistDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("artist"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ReleaseDb(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "format") val format: String?,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "thumb") val thumb: String,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(index = true, name = "artist")
    val artist: String
)