package rqk.footballmatchankosqlitetesting.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import rqk.footballmatchankosqlitetesting.R
import rqk.footballmatchankosqlitetesting.adapter.MainAdapterFavorite
import rqk.footballmatchankosqlitetesting.dbhelper.database
import rqk.footballmatchankosqlitetesting.model.Favorite
import rqk.footballmatchankosqlitetesting.view.DetailActivity


class FavoritesMatchFragment : Fragment(), AnkoComponent<Context> {
    private var events: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: MainAdapterFavorite
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MainAdapterFavorite(events) {
            context?.startActivity<DetailActivity>(
                "match" to "${it.eventId}",
                "home" to "${it.homeTeamId}",
                "away" to "${it.awayTeamId}"
            )
        }

        recyclerView.adapter = adapter
        showFavorites()
        swipeRefresh.onRefresh {
            events.clear()
            showFavorites()
        }
    }

    private fun showFavorites() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.OPEN_TABLE)
            val favorite =  result.parseList(classParser<Favorite>())
            events.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(context?.let { AnkoContext.create(it) }!!)
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                recyclerView = recyclerView {
                    id = R.id.recycler_view
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }
}