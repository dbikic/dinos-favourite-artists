package com.bikotron.dinosfavouriteartists.ui.features.artist

import androidx.annotation.MainThread
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.bikotron.data.IoThreads
import com.bikotron.dinosfavouriteartists.ui.BaseViewModel
import com.bikotron.domain.artists.models.Artist
import com.bikotron.domain.artists.usecases.ObserveArtistAndReleasesSortByYearUseCase
import com.bikotron.domain.releases.models.Release
import com.bikotron.domain.releases.usecases.RefreshReleasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val refreshReleasesUseCase: RefreshReleasesUseCase,
    private val observeArtistAndReleasesSortByYearUseCase: ObserveArtistAndReleasesSortByYearUseCase,
    @MainThread private val mainThreadScheduler: Scheduler,
    @IoThreads private val ioThreadsScheduler: Scheduler
) : BaseViewModel(mainThreadScheduler, ioThreadsScheduler) {

    private val _isRefreshing = mutableStateOf(true)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _artist = mutableStateOf<Artist?>(null)
    val artist: State<Artist?> get() = _artist

    private val _releases = mutableStateOf<List<Release>>(emptyList())
    val releases: State<List<Release>> = _releases

    private val _releaseDetail = mutableStateOf<Release?>(null)
    val releaseDetail: State<Release?> = _releaseDetail

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private var artistId: String = ""

    fun initialize(artistId: String) {
        this.artistId = artistId
        observeArtistAndReleasesSortByYearUseCase(artistId)
            .baseSubscribe {
                _artist.value = it.artist
                _releases.value = it.releases
            }
        refreshReleases()
    }

    fun refreshReleases() {
        _isRefreshing.value = true
        refreshReleasesUseCase(artistId)
            .baseSubscribe(
                onComplete = {
                    Timber.d("Releases refreshed!")
                    _isRefreshing.value = false
                },
                onError = {
                    Timber.e(it)
                    _isRefreshing.value = false
                    _errorMessage.value = "Error refreshing releases"
                }
            )
    }

    fun onReleaseClick(release: Release) {
        _releaseDetail.value = release
    }

    fun hideReleaseDetails() {
        _releaseDetail.value = null
    }

    fun onErrorMessageDisplayed() {
        _errorMessage.value = null
    }
}