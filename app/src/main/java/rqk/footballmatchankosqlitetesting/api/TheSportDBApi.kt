package rqk.footballmatchankosqlitetesting.api

import rqk.footballmatchankosqlitetesting.BuildConfig

object TheSportDBApi {
    fun getLastEvents(leagueId: Int?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + leagueId.toString()
    }

    fun getNextEvents(leagueId: Int?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + leagueId.toString()
    }

    fun getDetailEvent(eventId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + eventId
    }

    fun getDetailTeam(teamId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }
}