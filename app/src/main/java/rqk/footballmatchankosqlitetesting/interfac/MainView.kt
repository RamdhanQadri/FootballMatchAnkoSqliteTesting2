package rqk.footballmatchankosqlitetesting.interfac

import rqk.footballmatchankosqlitetesting.model.Event

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatch(data: List<Event>)
}