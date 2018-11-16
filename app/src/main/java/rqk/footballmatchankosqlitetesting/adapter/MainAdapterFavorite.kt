package rqk.footballmatchankosqlitetesting.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import rqk.footballmatchankosqlitetesting.layoutUI.LayoutUI
import rqk.footballmatchankosqlitetesting.model.Favorite
import rqk.footballmatchankosqlitetesting.utils.bold
import rqk.footballmatchankosqlitetesting.utils.formatDate
import rqk.footballmatchankosqlitetesting.utils.normal

class MainAdapterFavorite(
    private val events: List<Favorite>,
    private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<EventViewHolderFavorite>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolderFavorite {
        return EventViewHolderFavorite(
            LayoutUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolderFavorite, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size
}

class EventViewHolderFavorite(val view: View) : RecyclerView.ViewHolder(view) {
    private val eventDate: TextView = view.find(LayoutUI.textViewDate)
    private val homeTeamName: TextView = view.find(LayoutUI.textViewHomeTeam)
    private val homeTeamScore: TextView = view.find(LayoutUI.testViewHomeScore)
    private val awayTeamScore: TextView = view.find(LayoutUI.textViewAwayScore)
    private val awayTeamName: TextView = view.find(LayoutUI.textViewAwayTeam)

    fun bindItem(events: Favorite, listener: (Favorite) -> Unit) {
        eventDate.text = events.eventDate?.formatDate()
        homeTeamName.text = events.homeTeam
        homeTeamScore.text = events.homeScore
        awayTeamScore.text = events.awayScore
        awayTeamName.text = events.awayTeam

        val homeScore = events.homeScore?.toInt() ?: 0
        val awayScore = events.awayScore?.toInt() ?: 0

        if (homeScore - awayScore > 0) {
            homeTeamName.bold()
            awayTeamName.normal()
        } else {
            if (homeScore - awayScore < 0) {
                homeTeamName.normal()
                awayTeamName.bold()
            } else {
                homeTeamName.normal()
                awayTeamName.normal()
            }
        }

        itemView.setOnClickListener {
            listener(events)
        }
    }
}