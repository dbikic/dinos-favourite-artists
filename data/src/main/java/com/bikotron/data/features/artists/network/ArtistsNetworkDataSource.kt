package com.bikotron.data.features.artists.network

import com.bikotron.core.models.ArtistJson
import com.bikotron.data.BuildConfig
import com.bikotron.data.network.DiscogsApi
import io.reactivex.Single
import javax.inject.Inject

interface ArtistsNetworkDataSource {
    fun fetchArtist(id: String): Single<ArtistJson>
}

class ArtistsRetrofitDataSource @Inject constructor(
    private val discogsApi: DiscogsApi,
) : ArtistsNetworkDataSource {

    override fun fetchArtist(id: String): Single<ArtistJson> {
        return discogsApi.getArtist(id, BuildConfig.DISCOGS_API_KEY, BuildConfig.DISCOGS_API_SECRET)
    }
}