package com.bikotron.domain.artists.usecases

import com.bikotron.domain.artists.ArtistsRepository
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.subjects.CompletableSubject
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class RefreshFavouriteArtistsUseCaseTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var artistsRepository: ArtistsRepository

    @InjectMocks
    private lateinit var useCase: RefreshFavouriteArtistsUseCase

    @Test
    fun `When artists exist in the db then emit them`() {
        val publisher = CompletableSubject.create()
        whenever(artistsRepository.refreshFavouriteArtists()) doReturn publisher.hide()

        val testSubscriber = useCase().test()
        publisher.onComplete()

        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()
    }

    @Test
    fun `When observing artists return error then emit error`() {
        val publisher = CompletableSubject.create()
        whenever(artistsRepository.refreshFavouriteArtists()) doReturn publisher.hide()
        val error = Throwable("error")

        val testSubscriber = useCase().test()
        testSubscriber.onError(error)

        testSubscriber.assertError(error)
    }
}
