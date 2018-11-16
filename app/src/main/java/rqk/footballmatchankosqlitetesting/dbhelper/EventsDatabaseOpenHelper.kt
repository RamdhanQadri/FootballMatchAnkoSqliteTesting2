package rqk.footballmatchankosqlitetesting.dbhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import rqk.footballmatchankosqlitetesting.model.Favorite

class EventsDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Open.db", null, 3) {
    companion object {
        private var intance: EventsDatabaseOpenHelper? = null

        @Synchronized
        fun getIntance(ctx: Context): EventsDatabaseOpenHelper {
            if (intance == null) {
                intance = EventsDatabaseOpenHelper(ctx.applicationContext)
            }
            return intance as EventsDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Favorite.OPEN_TABLE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.EVENT_DATE to TEXT,
            Favorite.HOME_TEAM_ID to TEXT,
            Favorite.HOME_TEAM to TEXT,
            Favorite.HOME_TEAM_SCORE to TEXT,
            Favorite.AWAY_TEAM_ID to TEXT,
            Favorite.AWAY_TEAM to TEXT,
            Favorite.AWAY_TEAM_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.OPEN_TABLE, true)
    }
}

val Context.database: EventsDatabaseOpenHelper get() = EventsDatabaseOpenHelper.getIntance(applicationContext)