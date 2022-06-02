package com.bikotron.data.features.releases.db

import androidx.room.*

@Dao
interface ReleasesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreAll(releases: List<ReleaseDb>): List<Long>

    @Update
    fun update(release: ReleaseDb)

    @Transaction
    fun insertOrUpdate(releases: List<ReleaseDb>) {
        insertIgnoreAll(releases).forEachIndexed { index, result ->
            if (result == -1L) {
                update(releases[index])
            }
        }
    }
}