package com.bikotron.data.features.releases.network

import com.bikotron.core.models.ReleasesJson
import com.bikotron.data.BuildConfig
import com.bikotron.data.network.DiscogsApi
import io.reactivex.Observable
import javax.inject.Inject

interface ReleasesNetworkDataSource {
    fun fetchReleases(id: String): Observable<ReleasesJson>
}

class ReleasesRetrofitDataSource @Inject constructor(
    private val discogsApi: DiscogsApi,
) : ReleasesNetworkDataSource {

    override fun fetchReleases(id: String): Observable<ReleasesJson> {
        return recursivePagination(id)
    }

    private fun recursivePagination(id: String, currentPage: Int = 1): Observable<ReleasesJson> {
        return discogsApi.getReleases(id, currentPage, key = BuildConfig.DISCOGS_API_KEY, secret = BuildConfig.DISCOGS_API_SECRET)
            .concatMap {
                val pagination = it.pagination
                if (pagination.page == pagination.pages) {
                    Observable.just(it)
                } else {
                    Observable.just(it)
                        .concatWith(
                            recursivePagination(id, currentPage + 1)
                        )
                }
            }
    }
}