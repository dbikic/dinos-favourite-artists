package com.bikotron.domain.artists.usecases

import com.bikotron.domain.artists.ArtistsRepository
import com.bikotron.domain.artists.models.Artist
import com.bikotron.domain.artists.models.ArtistAndReleases
import com.bikotron.domain.releases.models.Release
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.processors.PublishProcessor
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class ObserveArtistAndReleasesSortByYearUseCaseTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var artistsRepository: ArtistsRepository

    @InjectMocks
    private lateinit var useCase: ObserveArtistAndReleasesSortByYearUseCase

    @Test
    fun `When artist and releases empty in the db then don't emit any item`() {
        val publisher = PublishProcessor.create<ArtistAndReleases>()
        whenever(artistsRepository.observeArtistsAndReleases(AN_ID)) doReturn publisher.onBackpressureLatest()

        val testSubscriber = useCase(AN_ID).test()

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isEmpty())
    }

    @Test
    fun `When artist and releases return error then emit error`() {
        val publisher = PublishProcessor.create<ArtistAndReleases>()
        whenever(artistsRepository.observeArtistsAndReleases(AN_ID)) doReturn publisher.onBackpressureLatest()
        val error = Throwable("error")

        val testSubscriber = useCase(AN_ID).test()
        testSubscriber.onError(error)

        testSubscriber.assertError(error)
    }

    @Test
    fun `When artist and releases exist in the db then emit them`() {
        val publisher = PublishProcessor.create<ArtistAndReleases>()
        whenever(artistsRepository.observeArtistsAndReleases(AN_ID)) doReturn publisher.onBackpressureLatest()

        val testSubscriber = useCase(AN_ID).test()
        publisher.onNext(
            ArtistAndReleases(
                artist = mock(),
                releases = mock(),
            )
        )

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isNotEmpty())
    }

    @Test
    fun `When artist and releases exist in the db but aren't sorted by year then sort them and emit them`() {
        val publisher = PublishProcessor.create<ArtistAndReleases>()
        whenever(artistsRepository.observeArtistsAndReleases(AN_ID)) doReturn publisher.onBackpressureLatest()

        val testSubscriber = useCase(AN_ID).test()
        publisher.onNext(
            ArtistAndReleases(
                artist = mock(),
                releases = listOf(
                    mock<Release>().apply {
                        whenever(this.year) doReturn 3
                    },
                    mock<Release>().apply {
                        whenever(this.year) doReturn 2
                    },
                    mock<Release>().apply {
                        whenever(this.year) doReturn 1
                    }
                ),
            )
        )

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isNotEmpty())
        val emittedReleases = testSubscriber.values()[0].releases
        assert(emittedReleases.isNotEmpty())
        assertEquals(emittedReleases[0].year, 1)
        assertEquals(emittedReleases[1].year, 2)
        assertEquals(emittedReleases[2].year, 3)
    }

    @Test
    fun `When artist and releases are emitted twice then also emit two events`() {
        val publisher = PublishProcessor.create<ArtistAndReleases>()
        whenever(artistsRepository.observeArtistsAndReleases(AN_ID)) doReturn publisher.onBackpressureLatest()

        val testSubscriber = useCase(AN_ID).test()
        publisher.onNext(
            ArtistAndReleases(
                artist = mock<Artist>().apply {
                    whenever(this.name) doReturn "Artist 1"
                },
                releases = mock(),
            )
        )
        publisher.onNext(
            ArtistAndReleases(
                artist = mock<Artist>().apply {
                    whenever(this.name) doReturn "Artist 2"
                },
                releases = mock(),
            )
        )

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isNotEmpty())
        assertEquals(testSubscriber.valueCount(), 2)
        assertEquals(testSubscriber.values().first().artist.name, "Artist 1")
        assertEquals(testSubscriber.values().last().artist.name, "Artist 2")
    }

    companion object {
        private const val AN_ID = "id"
    }
}