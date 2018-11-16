package rqk.footballmatchankosqlitetesting.presenter

import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mockito.Mockito.`when`
import rqk.footballmatchankosqlitetesting.TestContextProvider
import rqk.footballmatchankosqlitetesting.api.ApiRepository
import rqk.footballmatchankosqlitetesting.api.TheSportDBApi
import rqk.footballmatchankosqlitetesting.interfac.MainView
import rqk.footballmatchankosqlitetesting.model.Event
import rqk.footballmatchankosqlitetesting.model.EventResponse

class MainPresenterTest {
    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLastEvents() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        val idLast = 4332
        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getLastEvents(idLast)).await(),
                    EventResponse::class.java
                )
            )
                .thenReturn(response)

            presenter.getLastEvents(idLast)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatch(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getNextEvents() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        val idNext = 4332

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getNextEvents(idNext)).await(),
                    EventResponse::class.java
                )
            )
                .thenReturn(response)

            presenter.getNextEvents(idNext)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatch(events)
            Mockito.verify(view).hideLoading()
        }
    }
}