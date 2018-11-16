package rqk.footballmatchankosqlitetesting.presenter

import com.google.gson.Gson
import rqk.footballmatchankosqlitetesting.api.ApiRepository
import rqk.footballmatchankosqlitetesting.api.TheSportDBApi
import rqk.footballmatchankosqlitetesting.interfac.MainViewDetail
import rqk.footballmatchankosqlitetesting.model.EventResponse
import rqk.footballmatchankosqlitetesting.model.TeamsResponse
import rqk.footballmatchankosqlitetesting.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenterDetail(
    private val view: MainViewDetail,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailEvents(eventId: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailEvent(eventId)).await(),
                EventResponse::class.java

            )
            view.hideLoading()
            view.showMatch(data.events)
        }
    }

    fun getDetailTeams(teamId: String, homeTeam: Boolean = true) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailTeam(teamId)).await(),
                TeamsResponse::class.java

            )
            view.hideLoading()
            view.showTeam(data.teams, homeTeam)
        }
    }
}