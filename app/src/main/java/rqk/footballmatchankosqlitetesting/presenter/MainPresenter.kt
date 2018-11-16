package rqk.footballmatchankosqlitetesting.presenter

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rqk.footballmatchankosqlitetesting.api.ApiRepository
import rqk.footballmatchankosqlitetesting.api.TheSportDBApi
import rqk.footballmatchankosqlitetesting.interfac.MainView
import rqk.footballmatchankosqlitetesting.model.EventResponse
import rqk.footballmatchankosqlitetesting.utils.CoroutineContextProvider

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLastEvents(leagueId: Int?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getLastEvents(leagueId)).await(),
                EventResponse::class.java

            )
            view.showMatch(data.events)
            view.hideLoading()
        }
    }

    fun getNextEvents(leagueId: Int?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextEvents(leagueId)).await(),
                EventResponse::class.java

            )
            view.hideLoading()
            view.showMatch(data.events)
        }
    }
}