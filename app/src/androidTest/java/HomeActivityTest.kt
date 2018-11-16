
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rqk.footballmatchankosqlitetesting.R.id.*
import rqk.footballmatchankosqlitetesting.view.HomeActivity

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun appBehevior() {
        //Add Last Event To Favorite
        Thread.sleep(3000)
        onView(withId(last_match)).perform(click())
        Thread.sleep(3000)
        onView(withId(recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(recycler_view))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(10))
        Thread.sleep(3000)
        onView(withId(recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(3000)

        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
        pressBack()

        //Remove Last Event From Favorite
        Thread.sleep(3000)
        onView(withId(favorites)).perform(click())
        Thread.sleep(3000)
        onView(withId(recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(3000)

        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
        pressBack()

        //Add Next Event To Favorite
        Thread.sleep(3000)
        onView(withId(next_match)).perform(click())
        Thread.sleep(3000)
        onView(withId(recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(recycler_view))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(10))
        Thread.sleep(3000)
        onView(withId(recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(3000)

        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
        pressBack()

        //Remove Next Event From Favorite
        Thread.sleep(3000)
        onView(withId(favorites)).perform(click())
        Thread.sleep(3000)
        onView(withId(recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(3000)

        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
    }
}