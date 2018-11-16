package rqk.footballmatchankosqlitetesting.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import rqk.footballmatchankosqlitetesting.R
import rqk.footballmatchankosqlitetesting.R.id.*
import rqk.footballmatchankosqlitetesting.view.fragment.FavoritesMatchFragment
import rqk.footballmatchankosqlitetesting.view.fragment.LastMatchFragment
import rqk.footballmatchankosqlitetesting.view.fragment.NextMatchFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                last_match -> {
                    loadLastMatchFragment(savedInstanceState)
                }
                next_match -> {
                    loadNextMatchFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesMatchFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = last_match
    }

    private fun loadLastMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, FavoritesMatchFragment(), FavoritesMatchFragment::class.java.simpleName)
                .commit()
        }
    }
}