package com.bikotron.data.features.artists.di

import com.bikotron.data.features.artists.ArtistsRepositoryProvider
import com.bikotron.data.features.artists.db.ArtistsDiskDataSource
import com.bikotron.data.features.artists.db.ArtistsRoomDataSource
import com.bikotron.data.features.artists.network.ArtistsNetworkDataSource
import com.bikotron.data.features.artists.network.ArtistsRetrofitDataSource
import com.bikotron.domain.artists.ArtistsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistsModule {

    @Binds
    abstract fun provideArtistsRepository(provider: ArtistsRepositoryProvider): ArtistsRepository

    @Binds
    abstract fun provideArtistsNetworkDataSource(dataSource: ArtistsRetrofitDataSource): ArtistsNetworkDataSource

    @Binds
    abstract fun provideArtistsDiskDataSource(dataSource: ArtistsRoomDataSource): ArtistsDiskDataSource
}