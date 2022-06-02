package com.bikotron.dinosfavouriteartists.di

import androidx.annotation.MainThread
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.bikotron.data.ComputationThreads
import com.bikotron.data.IoThreads
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SchedulersModule {

    @Provides
    @Singleton
    @MainThread
    fun mainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @ComputationThreads
    fun computationScheduler(): Scheduler = Schedulers.computation()

    @Provides
    @Singleton
    @IoThreads
    fun ioScheduler(): Scheduler = Schedulers.io()

}

