package rqk.footballmatchankosqlitetesting.presenter

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import rqk.footballmatchankosqlitetesting.TestContextProvider
import rqk.footballmatchankosqlitetesting.api.ApiRepository
import rqk.footballmatchankosqlitetesting.api.TheSportDBApi
import rqk.footballmatchankosqlitetesting.interfac.MainViewDetail
import rqk.footballmatchankosqlitetesting.model.Event
import rqk.footballmatchankosqlitetesting.model.EventResponse

class MainPresenterDetailTest {
    @Mock
    private
    lateinit var view: MainViewDetail

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenter: MainPresenterDetail

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenterDetail(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getDetailEvents() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        val idEventInterVSFrosinone = 582175
        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getDetailEvent(idEventInterVSFrosinone.toString())).await(),
                    EventResponse::class.java
                )
            )
                .thenReturn(response)

            presenter.getDetailEvents(idEventInterVSFrosinone.toString())

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatch(events)
            Mockito.verify(view).hideLoading()
        }
    }
}