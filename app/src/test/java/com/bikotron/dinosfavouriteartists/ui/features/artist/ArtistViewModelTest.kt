package com.bikotron.dinosfavouriteartists.ui.features.artist

import com.bikotron.domain.artists.models.ArtistAndReleases
import com.bikotron.domain.artists.usecases.ObserveArtistAndReleasesSortByYearUseCase
import com.bikotron.domain.releases.usecases.RefreshReleasesUseCase
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject
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
class ArtistViewModelTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var refreshReleasesUseCase: RefreshReleasesUseCase

    @Mock
    private lateinit var observeArtistAndReleasesSortByYearUseCase: ObserveArtistAndReleasesSortByYearUseCase

    private lateinit var viewModel: ArtistViewModel

    private val refreshReleasesPublisher = CompletableSubject.create()
    private val observeArtistAndReleasesSortByYearPublisher = PublishProcessor.create<ArtistAndReleases>()

    @Before
    fun setup() {
        whenever(refreshReleasesUseCase(AN_ID)) doReturn refreshReleasesPublisher.hide()
        whenever(observeArtistAndReleasesSortByYearUseCase(AN_ID)) doReturn observeArtistAndReleasesSortByYearPublisher.hide()
        viewModel = ArtistViewModel(
            refreshReleasesUseCase, observeArtistAndReleasesSortByYearUseCase, Schedulers.trampoline(), Schedulers.trampoline()
        )
    }

    @Test
    fun `When initializing then refresh and observe artists and releases`() {
        assertTrue(viewModel.isRefreshing.value)
        assertNull(viewModel.artist.value)
        assertTrue(viewModel.releases.value.isEmpty())

        viewModel.initialize(AN_ID)
        refreshReleasesPublisher.onComplete()
        observeArtistAndReleasesSortByYearPublisher.onNext(
            ArtistAndReleases(
                artist = mock(),
                releases = listOf(mock(), mock()),
            )
        )

        assertFalse(viewModel.isRefreshing.value)
        assertNotNull(viewModel.artist.value)
        assertTrue(viewModel.releases.value.isNotEmpty())
    }

    @Test
    fun `When refreshing fails then hide the loader and show the error message`() {
        viewModel.initialize(AN_ID)

        refreshReleasesPublisher.onError(Throwable())
        viewModel.refreshReleases()

        assertFalse(viewModel.isRefreshing.value)
        assertNotNull(viewModel.errorMessage.value)
    }

    @Test
    fun `When dismissing error message on error then hide it`() {
        viewModel.initialize(AN_ID)
        refreshReleasesPublisher.onError(Throwable())
        viewModel.refreshReleases()

        assertNotNull(viewModel.errorMessage.value)

        viewModel.onErrorMessageDisplayed()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `When release clicked then display the details and when dismissed then hide the details`() {
        assertNull(viewModel.releaseDetail.value)

        viewModel.onReleaseClick(mock())

        assertNotNull(viewModel.releaseDetail.value)

        viewModel.hideReleaseDetails()

        assertNull(viewModel.releaseDetail.value)
    }

    companion object {
        private const val AN_ID = "id"
    }
}