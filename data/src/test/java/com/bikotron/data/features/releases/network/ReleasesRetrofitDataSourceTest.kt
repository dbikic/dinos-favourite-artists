package com.bikotron.data.features.releases.network

import com.bikotron.core.models.PaginationJson
import com.bikotron.core.models.ReleaseJson
import com.bikotron.core.models.ReleasesJson
import com.bikotron.data.network.DiscogsApi
import com.bikotron.sharedtest.RxScheduleOverrideRule
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class ReleasesRetrofitDataSourceTest {

    @get:Rule
    var rxRule: TestRule = RxScheduleOverrideRule()

    @Mock
    private lateinit var discogsApi: DiscogsApi

    @InjectMocks
    private lateinit var dataSource: ReleasesRetrofitDataSource

    @Test
    fun `When fetching releases throws an error then propagate the error`() {
        val publisher = PublishSubject.create<ReleasesJson>()
        whenever(discogsApi.getReleases(any(), any(), any(), any(), any(), any())) doReturn publisher.hide()
        val error = Throwable("error")

        val testSubscriber = dataSource.fetchReleases(AN_ID).test()
        testSubscriber.onError(error)

        testSubscriber.assertError(error)
    }

    @Test
    fun `When fetching releases returns a response with only one page then just return those releases`() {
        val publisher = PublishSubject.create<ReleasesJson>()
        whenever(discogsApi.getReleases(any(), any(), any(), any(), any(), any())) doReturn publisher.hide()

        val testSubscriber = dataSource.fetchReleases(AN_ID).test()
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 1, pages = 1),
                releases = listOf(mock()),
            )
        )

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isNotEmpty())
        assertEquals(testSubscriber.valueCount(), 1)
    }

    @Test
    fun `When fetching releases returns multiple pages then fetch all the pages and propagate the releases`() {
        val publisher = PublishSubject.create<ReleasesJson>()
        whenever(discogsApi.getReleases(any(), any(), any(), any(), any(), any())) doReturn publisher.hide()

        val testSubscriber = dataSource.fetchReleases(AN_ID).test()
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 1, pages = 4),
                releases = listOf(
                    mock<ReleaseJson>().apply {
                        whenever(this.title) doReturn "Page 1"
                    }
                ),
            )
        )
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 2, pages = 4),
                releases = listOf(
                    mock<ReleaseJson>().apply {
                        whenever(this.title) doReturn "Page 2"
                    }
                ),
            )
        )
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 3, pages = 4),
                releases = listOf(
                    mock<ReleaseJson>().apply {
                        whenever(this.title) doReturn "Page 3"
                    }
                ),
            )
        )
        publisher.onNext(
            ReleasesJson(
                pagination = PaginationJson(page = 4, pages = 4),
                releases = listOf(
                    mock<ReleaseJson>().apply {
                        whenever(this.title) doReturn "Page 4"
                    }
                ),
            )
        )

        testSubscriber.assertNoErrors()
        assert(testSubscriber.values().isNotEmpty())
        assertEquals(testSubscriber.valueCount(), 4)
        assertEquals(testSubscriber.values()[0].releases.first().title, "Page 1")
        assertEquals(testSubscriber.values()[1].releases.first().title, "Page 2")
        assertEquals(testSubscriber.values()[2].releases.first().title, "Page 3")
        assertEquals(testSubscriber.values()[3].releases.first().title, "Page 4")
    }

    companion object {
        private const val AN_ID = "id"
    }
}