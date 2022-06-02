package com.bikotron.data.network

import com.bikotron.core.models.ArtistJson
import com.bikotron.core.models.ReleasesJson
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscogsApi {

    @GET("/artists/{id}")
    fun getArtist(
        @Path("id") id: String,
        @Query("key") key: String,
        @Query("secret") secret: String,
    ): Single<ArtistJson>

    @GET("/artists/{id}/releases")
    fun getReleases(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "year",
        @Query("sort_order") sortOrder: String = "asc",
        @Query("key") key: String,
        @Query("secret") secret: String,
    ): Observable<ReleasesJson>
}