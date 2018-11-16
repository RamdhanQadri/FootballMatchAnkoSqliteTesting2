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
import android.widget.ProgressBar
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import rqk.footballmatchankosqlitetesting.R
import rqk.footballmatchankosqlitetesting.adapter.MainAdapter
import rqk.footballmatchankosqlitetesting.api.ApiRepository
import rqk.footballmatchankosqlitetesting.interfac.MainView
import rqk.footballmatchankosqlitetesting.model.Event
import rqk.footballmatchankosqlitetesting.presenter.MainPresenter
import rqk.footballmatchankosqlitetesting.utils.invisible
import rqk.footballmatchankosqlitetesting.utils.visible
import rqk.footballmatchankosqlitetesting.view.DetailActivity

private const val leagueId: Int = 4332

class NextMatchFragment : Fragment(), AnkoComponent<Context>, MainView {
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MainAdapter(events) {
            context?.startActivity<DetailActivity>(
                "match" to "${it.eventId}",
                "home" to "${it.homeTeamId}",
                "away" to "${it.awayTeamId}"
            )
        }
        recyclerView.adapter = adapter

        presenter = MainPresenter(this, ApiRepository(), Gson())
        presenter.getNextEvents(leagueId)

        swipeRefresh.onRefresh {
            presenter.getNextEvents(leagueId)
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

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recyclerView = recyclerView {
                        id = R.id.recycler_view
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatch(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}