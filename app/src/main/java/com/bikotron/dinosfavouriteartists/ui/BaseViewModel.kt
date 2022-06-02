package com.bikotron.dinosfavouriteartists.ui

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.bikotron.data.IoThreads
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(
    @MainThread private val mainThreadScheduler: Scheduler,
    @IoThreads private val ioThreadsScheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun Disposable.addToCompositeDisposable() {
        compositeDisposable.add(this)
    }

    fun <T> Single<T>.baseSubscribe(
        subscribeOn: Scheduler? = ioThreadsScheduler,
        observeOn: Scheduler? = mainThreadScheduler,
        doOnSubscribe: (() -> Unit)? = null,
        doAfterTerminate: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) {
        this.subscribeOn(subscribeOn)
            .run {
                if (observeOn != null) {
                    observeOn(observeOn)
                } else {
                    this
                }
            }
            .doOnSubscribe { doOnSubscribe?.let { it() } }
            .doAfterTerminate { doAfterTerminate?.let { it() } }
            .subscribe(
                { onSuccess(it) },
                { onError?.invoke(it) }
            ).addToCompositeDisposable()
    }

    fun <T> Observable<T>.baseSubscribe(
        subscribeOn: Scheduler? = ioThreadsScheduler,
        observeOn: Scheduler? = mainThreadScheduler,
        doOnSubscribe: (() -> Unit)? = null,
        doAfterTerminate: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) {
        this.subscribeOn(subscribeOn)
            .run {
                if (observeOn != null) {
                    observeOn(observeOn)
                } else {
                    this
                }
            }
            .doOnSubscribe { doOnSubscribe?.let { it() } }
            .doAfterTerminate { doAfterTerminate?.let { it() } }
            .subscribe(
                { onSuccess(it) },
                { onError?.invoke(it) }
            ).addToCompositeDisposable()
    }

    fun <T> Flowable<T>.baseSubscribe(
        subscribeOn: Scheduler = ioThreadsScheduler,
        observeOn: Scheduler? = mainThreadScheduler,
        doOnSubscribe: (() -> Unit)? = null,
        doAfterTerminate: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) {
        this.subscribeOn(subscribeOn)
            .run {
                if (observeOn != null) {
                    observeOn(observeOn)
                } else {
                    this
                }
            }
            .doOnSubscribe { doOnSubscribe?.let { it() } }
            .doAfterTerminate { doAfterTerminate?.let { it() } }
            .subscribe(
                { onSuccess(it) },
                { onError?.invoke(it) }
            ).addToCompositeDisposable()
    }

    fun Completable.baseSubscribe(
        subscribeOn: Scheduler? = ioThreadsScheduler,
        observeOn: Scheduler? = mainThreadScheduler,
        doOnSubscribe: (() -> Unit)? = null,
        doAfterTerminate: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: () -> Unit
    ) {
        this.subscribeOn(subscribeOn)
            .run {
                if (observeOn != null) {
                    observeOn(observeOn)
                } else {
                    this
                }
            }
            .doOnSubscribe { doOnSubscribe?.let { it() } }
            .doAfterTerminate { doAfterTerminate?.let { it() } }
            .subscribe(
                { onComplete() },
                { onError?.invoke(it) }
            ).addToCompositeDisposable()
    }
}