package com.bikotron.data.features.releases.di

import com.bikotron.data.features.releases.ReleasesRepositoryProvider
import com.bikotron.data.features.releases.db.ReleasesDiskDataSource
import com.bikotron.data.features.releases.db.ReleasesRoomDataSource
import com.bikotron.data.features.releases.network.ReleasesNetworkDataSource
import com.bikotron.data.features.releases.network.ReleasesRetrofitDataSource
import com.bikotron.domain.releases.ReleasesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReleasesModule {

    @Binds
    abstract fun provideReleasesRepository(provider: ReleasesRepositoryProvider): ReleasesRepository

    @Binds
    abstract fun provideReleasesNetworkDataSource(dataSource: ReleasesRetrofitDataSource): ReleasesNetworkDataSource

    @Binds
    abstract fun provideReleasesDiskDataSource(dataSource: ReleasesRoomDataSource): ReleasesDiskDataSource
}