package rqk.footballmatchankosqlitetesting.interfac

import rqk.footballmatchankosqlitetesting.model.Event

interface MainViewDetail {
    fun showLoading()
    fun hideLoading()
    fun showMatch(data: List<Event>)
    fun showTeam(data: List<Event>, homeTeam: Boolean)
}