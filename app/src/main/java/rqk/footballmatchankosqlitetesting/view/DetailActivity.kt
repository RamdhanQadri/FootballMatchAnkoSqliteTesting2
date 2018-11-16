package rqk.footballmatchankosqlitetesting.view

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import rqk.footballmatchankosqlitetesting.R
import rqk.footballmatchankosqlitetesting.R.color.colorAccent
import rqk.footballmatchankosqlitetesting.R.drawable.ic_add_to_favorites
import rqk.footballmatchankosqlitetesting.R.drawable.ic_added_to_favorites
import rqk.footballmatchankosqlitetesting.R.id.add_to_favorite
import rqk.footballmatchankosqlitetesting.R.menu.detail_menu
import rqk.footballmatchankosqlitetesting.api.ApiRepository
import rqk.footballmatchankosqlitetesting.dbhelper.database
import rqk.footballmatchankosqlitetesting.interfac.MainViewDetail
import rqk.footballmatchankosqlitetesting.model.Event
import rqk.footballmatchankosqlitetesting.model.Favorite
import rqk.footballmatchankosqlitetesting.presenter.MainPresenterDetail
import rqk.footballmatchankosqlitetesting.utils.formatDate
import rqk.footballmatchankosqlitetesting.utils.invisible
import rqk.footballmatchankosqlitetesting.utils.visible

class DetailActivity : AppCompatActivity(), MainViewDetail {

    private val presenterDetail: MainPresenterDetail = MainPresenterDetail(this, ApiRepository(), Gson())
    private lateinit var progressBarDetail: ProgressBar

    private lateinit var scrollV: ScrollView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var textViewDateDetail: TextView
    private lateinit var textViewHomeTeamDetail: TextView
    private lateinit var textViewHomeScoreDetail: TextView
    private lateinit var textViewAwayScoreDetail: TextView
    private lateinit var textViewAwayTeamDetail: TextView

    private lateinit var textViewGoolsHome: TextView
    private lateinit var textViewGoolsAway: TextView
    private lateinit var textViewShotsHome: TextView
    private lateinit var textViewShotsAway: TextView

    private lateinit var textViewGoolKeeperHome: TextView
    private lateinit var textViewGoolKeeperAway: TextView
    private lateinit var textViewDefenseHome: TextView
    private lateinit var textViewDefenseAway: TextView
    private lateinit var textViewMidfieldHome: TextView
    private lateinit var textViewMidfieldAway: TextView
    private lateinit var textViewForwardHome: TextView
    private lateinit var textViewForwardAway: TextView
    private lateinit var textViewSubstitutesHome: TextView
    private lateinit var textViewSubstitutesAway: TextView


    private lateinit var imageViewHomeTeamDetail: ImageView
    private lateinit var imageViewAwayTeamDetail: ImageView

    private lateinit var id: Event
    private lateinit var matchEvent: String
    private lateinit var homeEvent: String
    private lateinit var awayEvent: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        matchEvent = intent.getStringExtra("match")
        homeEvent = intent.getStringExtra("home")
        awayEvent = intent.getStringExtra("away")

        supportActionBar?.title = getString(R.string.match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                scrollV = scrollView {
                    isVerticalScrollBarEnabled = false

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        lparams(matchParent, wrapContent)

                        //Grup 1
                        linearLayout {
                            backgroundColor = Color.GRAY
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.VERTICAL

                            textViewDateDetail = textView {
                                textColor = Color.BLACK
                                textSize = 14f
                                textColorResource = android.R.color.black
                            }.lparams(wrapContent, wrapContent)

                            //------------------------------------
                            linearLayout {
                                padding = dip(3)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER
                                weightSum = 3F

                                linearLayout {
                                    gravity = Gravity.CENTER
                                    orientation = LinearLayout.VERTICAL

                                    imageViewHomeTeamDetail = imageView {
                                    }.lparams(dip(50), dip(50))

                                    textViewHomeTeamDetail = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 16f
                                        typeface = Typeface.DEFAULT_BOLD
                                    }.lparams(width = dip(80), height = wrapContent)

                                }.lparams {
                                    topMargin = dip(5)
                                    weight = 1F
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    gravity = Gravity.CENTER
                                    linearLayout {
                                        gravity = Gravity.CENTER

                                        textViewHomeScoreDetail = textView {
                                            textSize = 16f
                                            textColorResource = android.R.color.black
                                        }
                                    }.lparams {
                                        topMargin = dip(5)
                                        weight = 0.1F
                                    }

                                    linearLayout {
                                        gravity = Gravity.CENTER

                                        textView {
                                            textResource = R.string.match
                                            textSize = 16f
                                        }
                                    }.lparams {
                                        topMargin = dip(5)
                                        weight = 0.1F
                                    }

                                    linearLayout {
                                        gravity = Gravity.CENTER

                                        textViewAwayScoreDetail = textView {
                                            textSize = 16f
                                            textColorResource = android.R.color.black
                                        }
                                    }.lparams {
                                        topMargin = dip(5)
                                        weight = 0.1F
                                    }
                                }.lparams {
                                    topMargin = dip(5)
                                    weight = 1F
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER
                                    orientation = LinearLayout.VERTICAL

                                    imageViewAwayTeamDetail = imageView {
                                    }.lparams(dip(50), dip(50))

                                    textViewAwayTeamDetail = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 16f
                                        typeface = Typeface.DEFAULT_BOLD
                                    }.lparams(width = dip(80), height = wrapContent)

                                }.lparams {
                                    topMargin = dip(5)
                                    weight = 1F
                                }
                            }.lparams(matchParent, matchParent)
                        }.lparams(matchParent, matchParent) {
                            setMargins(dip(5), dip(5), dip(5), dip(5))
                        }

                        //Grup 2
                        linearLayout {
                            backgroundColor = Color.GRAY
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.VERTICAL

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewGoolsHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.gools
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewGoolsAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewShotsHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.shots
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewShotsAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)
                        }.lparams(matchParent, matchParent) {
                            setMargins(dip(5), dip(5), dip(5), dip(5))
                        }

                        linearLayout {
                            gravity = Gravity.CENTER

                            textView {
                                gravity = Gravity.CENTER
                                textResource = R.string.lineups
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = wrapContent, height = wrapContent) {
                                setMargins(dip(2), dip(2), dip(2), dip(2))
                            }
                        }.lparams(matchParent, matchParent) {
                            setMargins(dip(5), dip(1), dip(5), dip(1))
                        }

                        //Grup 3
                        linearLayout {
                            backgroundColor = Color.GRAY
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.VERTICAL

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewGoolKeeperHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.gool_keeper
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewGoolKeeperAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewDefenseHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.defense
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewDefenseAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewMidfieldHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.midfield
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewMidfieldAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewForwardHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.forward
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewForwardAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)

                            //------------------------------------
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewSubstitutesHome = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textView {
                                        gravity = Gravity.CENTER
                                        textResource = R.string.substitutes
                                        textSize = 16f
                                    }.lparams(width = wrapContent, height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER

                                    textViewSubstitutesAway = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 15f
                                    }.lparams(width = dip(80), height = wrapContent) {
                                        setMargins(dip(2), dip(2), dip(2), dip(2))
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }.lparams(width = matchParent, height = wrapContent)

                        }.lparams(matchParent, matchParent) {
                            setMargins(dip(5), dip(5), dip(5), dip(5))
                        }
                    }
                }
            }

            progressBarDetail = progressBar {
            }
        }

        favoriteState()
        presenterDetail.getDetailEvents(matchEvent)
        presenterDetail.getDetailTeams(homeEvent)
        presenterDetail.getDetailTeams(awayEvent, false)

        swipeRefreshLayout.onRefresh {
            presenterDetail.getDetailEvents(matchEvent)
            presenterDetail.getDetailTeams(homeEvent)
            presenterDetail.getDetailTeams(awayEvent, false)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.OPEN_TABLE)
                .whereArgs(
                    "(EVENT_ID = {match})", "match" to matchEvent
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBarDetail.visible()
    }

    override fun hideLoading() {
        progressBarDetail.invisible()
    }

    override fun showMatch(data: List<Event>) {
        val first = data.first()
        id = Event(
            eventId = first.eventId,
            eventDate = first.eventDate,
            homeTeamId = first.homeTeamId,
            homeTeam = first.homeTeam,
            homeScore = first.homeScore,
            awayTeamId = first.awayTeamId,
            awayTeam = first.awayTeam,
            awayScore = first.awayScore
        )
        swipeRefreshLayout.isRefreshing = false
        textViewDateDetail.text = data[0].eventDate?.formatDate()
        textViewHomeTeamDetail.text = data[0].homeTeam
        textViewHomeScoreDetail.text = data[0].homeScore
        textViewAwayScoreDetail.text = data[0].awayScore
        textViewAwayTeamDetail.text = data[0].awayTeam
        textViewGoolsHome.text = data[0].homeGoalDetails
        textViewGoolsAway.text = data[0].awayGoalDetails
        textViewShotsHome.text = data[0].homeShots
        textViewShotsAway.text = data[0].awayShots
        textViewGoolKeeperHome.text = data[0].homeGoalKeeper
        textViewGoolKeeperAway.text = data[0].awayGoalKeeper
        textViewDefenseHome.text = data[0].homeDefense
        textViewDefenseAway.text = data[0].awayDefense
        textViewMidfieldHome.text = data[0].homeMidfield
        textViewMidfieldAway.text = data[0].awayMidfield
        textViewForwardHome.text = data[0].homeForward
        textViewForwardAway.text = data[0].awayForward
        textViewSubstitutesHome.text = data[0].homeSubstitutes
        textViewSubstitutesAway.text = data[0].awaySubstitutes
    }

    override fun showTeam(data: List<Event>, homeTeam: Boolean) {
        Glide.with(this).load(data[0].teamBadge)
            .into(if (homeTeam) imageViewHomeTeamDetail else imageViewAwayTeamDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorites() else addEventFavorites()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addEventFavorites() {
        try {
            database.use {
                insert(
                    Favorite.OPEN_TABLE,
                    Favorite.EVENT_ID to id.eventId,
                    Favorite.EVENT_DATE to id.eventDate,
                    Favorite.HOME_TEAM_ID to id.homeTeamId,
                    Favorite.HOME_TEAM to id.homeTeam,
                    Favorite.HOME_TEAM_SCORE to id.homeScore,
                    Favorite.AWAY_TEAM_ID to id.awayTeamId,
                    Favorite.AWAY_TEAM to id.awayTeam,
                    Favorite.AWAY_TEAM_SCORE to id.awayScore
                )
            }
            swipeRefreshLayout.snackbar(R.string.added)
        } catch (e: SQLiteConstraintException) {
            swipeRefreshLayout.snackbar(e.localizedMessage)
        }
    }

    private fun removeFromFavorites() {
        try {
            database.use {
                delete(
                    Favorite.OPEN_TABLE,
                    "(EVENT_ID = {match})", "match" to matchEvent
                )
            }
            swipeRefreshLayout.snackbar(R.string.removed)
        } catch (e: SQLiteConstraintException) {
            swipeRefreshLayout.snackbar(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}