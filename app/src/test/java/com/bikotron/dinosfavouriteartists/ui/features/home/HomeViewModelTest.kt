package com.bikotron.dinosfavouriteartists.ui.features.home

import com.bikotron.domain.artists.models.Artist
import com.bikotron.domain.artists.usecases.ObserveFavouriteArtistsUseCase
import com.bikotron.domain.artists.usecases.RefreshFavouriteArtistsUseCase
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var refreshFavouriteArtistsUseCase: RefreshFavouriteArtistsUseCase

    @Mock
    private lateinit var observeFavouriteArtistsUseCase: ObserveFavouriteArtistsUseCase

    private lateinit var viewModel: HomeViewModel

    private val refreshFavouriteArtistsPublisher = CompletableSubject.create()
    private val observeFavouriteArtistsPublisher = PublishSubject.create<List<Artist>>()

    @Before
    fun setup() {
        whenever(refreshFavouriteArtistsUseCase()) doReturn refreshFavouriteArtistsPublisher
        whenever(observeFavouriteArtistsUseCase()) doReturn observeFavouriteArtistsPublisher
        viewModel = HomeViewModel(
            refreshFavouriteArtistsUseCase, observeFavouriteArtistsUseCase, Schedulers.trampoline(), Schedulers.trampoline()
        )
    }

    @Test
    fun `When view model is created then refresh and observe favourite artists`() {
        assertTrue(viewModel.isRefreshing.value)
        assertTrue(viewModel.artists.value.isEmpty())

        refreshFavouriteArtistsPublisher.onComplete()
        observeFavouriteArtistsPublisher.onNext(listOf(mock(), mock()))

        assertFalse(viewModel.isRefreshing.value)
        assertTrue(viewModel.artists.value.isNotEmpty())
    }

    @Test
    fun `When refreshing fails then hide the loader and show the error message`() {
        refreshFavouriteArtistsPublisher.onError(Throwable())
        viewModel.refresh()

        assertFalse(viewModel.isRefreshing.value)
        assertNotNull(viewModel.errorMessage.value)
    }

    @Test
    fun `When dismissing error message on error then hide it`() {
        refreshFavouriteArtistsPublisher.onError(Throwable())
        viewModel.refresh()

        assertNotNull(viewModel.errorMessage.value)

        viewModel.onErrorMessageDisplayed()

        assertNull(viewModel.errorMessage.value)
    }
}