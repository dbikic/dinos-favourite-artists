package com.bikotron.data

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoThreads

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ComputationThreads