package com.bikotron.dinosfavouriteartists.ui.features.home

import androidx.annotation.MainThread
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.bikotron.data.IoThreads
import com.bikotron.dinosfavouriteartists.ui.BaseViewModel
import com.bikotron.domain.artists.models.Artist
import com.bikotron.domain.artists.usecases.ObserveFavouriteArtistsUseCase
import com.bikotron.domain.artists.usecases.RefreshFavouriteArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val refreshFavouriteArtistsUseCase: RefreshFavouriteArtistsUseCase,
    observeFavouriteArtistsUseCase: ObserveFavouriteArtistsUseCase,
    @MainThread private val mainThreadScheduler: Scheduler,
    @IoThreads private val ioThreadsScheduler: Scheduler
) : BaseViewModel(mainThreadScheduler, ioThreadsScheduler) {

    private val _isRefreshing = mutableStateOf(true)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _artists = mutableStateOf<List<Artist>>(emptyList())
    val artists: State<List<Artist>> = _artists

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        refresh()
        observeFavouriteArtistsUseCase()
            .baseSubscribe {
                _artists.value = it
            }
    }

    fun refresh() {
        _isRefreshing.value = true
        refreshFavouriteArtistsUseCase()
            .baseSubscribe(
                onComplete = {
                    Timber.d("Favourite artists refreshed!")
                    _isRefreshing.value = false
                },
                onError = {
                    Timber.e(it)
                    _isRefreshing.value = false
                    _errorMessage.value = "Error refreshing artists"
                }
            )
    }

    fun onErrorMessageDisplayed() {
        _errorMessage.value = null
    }
}