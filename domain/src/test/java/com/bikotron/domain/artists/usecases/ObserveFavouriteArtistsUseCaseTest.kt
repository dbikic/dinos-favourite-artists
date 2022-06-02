package com.bikotron.domain.artists.usecases

import com.bikotron.domain.artists.ArtistsRepository
import com.bikotron.domain.artists.models.Artist
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.subjects.PublishSubject
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
class ObserveFavouriteArtistsUseCaseTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var artistsRepository: ArtistsRepository

    @InjectMocks
    private lateinit var useCase: ObserveFavouriteArtistsUseCase

    @Test
    fun `When artists are empty in the db then don't emit any item`() {
        val publisher = PublishSubject.create<List<Artist>>()
        whenever(artistsRepository.observeArtists()) doReturn publisher.hide()

        val testSubscriber = useCase().test()

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isEmpty())
    }

    @Test
    fun `When observing artists return error then emit error`() {
        val publisher = PublishSubject.create<List<Artist>>()
        whenever(artistsRepository.observeArtists()) doReturn publisher.hide()
        val error = Throwable("error")

        val testSubscriber = useCase().test()
        testSubscriber.onError(error)

        testSubscriber.assertError(error)
    }

    @Test
    fun `When artists exist in the db then emit them`() {
        val publisher = PublishSubject.create<List<Artist>>()
        whenever(artistsRepository.observeArtists()) doReturn publisher.hide()

        val testSubscriber = useCase().test()
        publisher.onNext(listOf(mock()))

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isNotEmpty())
    }
}