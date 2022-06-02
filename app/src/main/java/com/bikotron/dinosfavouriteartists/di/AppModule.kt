package com.bikotron.dinosfavouriteartists.di

import com.bikotron.data.DataModule
import com.bikotron.data.features.artists.di.ArtistsModule
import com.bikotron.data.features.releases.di.ReleasesModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        SchedulersModule::class,
        DataModule::class,
        ArtistsModule::class,
        ReleasesModule::class,
    ]
)
object AppModule