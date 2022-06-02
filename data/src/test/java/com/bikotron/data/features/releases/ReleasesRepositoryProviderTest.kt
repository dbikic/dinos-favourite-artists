package com.bikotron.data.features.releases

import com.bikotron.core.models.PaginationJson
import com.bikotron.core.models.ReleaseJson
import com.bikotron.core.models.ReleasesJson
import com.bikotron.data.features.releases.db.ReleasesDiskDataSource
import com.bikotron.data.features.releases.network.ReleasesNetworkDataSource
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.subjects.PublishSubject
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*


@RunWith(MockitoJUnitRunner::class)
class ReleasesRepositoryProviderTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var networkDataSource: ReleasesNetworkDataSource

    @Mock
    private lateinit var diskDataSource: ReleasesDiskDataSource

    @InjectMocks
    private lateinit var repository: ReleasesRepositoryProvider

    @Test
    fun `When refreshing releases fails then don't store anything in the database`() {
        val publisher = PublishSubject.create<ReleasesJson>()
        whenever(networkDataSource.fetchReleases(AN_ID)) doReturn publisher.hide()
        val error = Throwable("error")

        val testSubscriber = repository.refreshReleases(AN_ID).test()
        testSubscriber.onError(error)

        verifyZeroInteractions(diskDataSource)
        testSubscriber.assertError(error)
    }

    @Test
    fun `When fetching releases returns multiple pages then fetch all the pages and propagate the releases`() {
        val publisher = PublishSubject.create<ReleasesJson>()
        whenever(networkDataSource.fetchReleases(AN_ID)) doReturn publisher.hide()
        val firstReleases = listOf<ReleaseJson>(mock(), mock(), mock())
        val secondReleases = listOf<ReleaseJson>(mock())

        val testSubscriber = repository.refreshReleases(AN_ID).test()
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 1, pages = 2),
                releases = firstReleases,
            )
        )
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 2, pages = 2),
                releases = secondReleases,
            )
        )

        testSubscriber.assertNoErrors()
        verify(diskDataSource).storeReleases(AN_ID, firstReleases)
        verify(diskDataSource).storeReleases(AN_ID, secondReleases)
    }

    companion object {
        private const val AN_ID = "id"
    }
}