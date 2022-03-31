package com.example.lv1


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText


@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MovieDetailActivity> = ActivityScenarioRule(MovieDetailActivity::class.java)

    fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>(){
        override fun describeTo(description: Description) {
            description.appendText("Drawable does not contain image with id: $id")
        }

        override fun matchesSafely(item: View): Boolean {
            val context:Context = item.context
            val bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
            return item is ImageView && item.drawable.toBitmap().sameAs(bitmap)
        }

    }

    @Test
    fun testDetailActivityInstantiation(){
        //val pokreniDetalje: Intent = Intent(MovieDetailActivity::javaClass.name)
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Black Widow")
        val scenario = launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).check(matches(withText("Black Widow")))
        onView(withId(R.id.movie_genre)).check(matches(withText("adventure")))
        onView(withId(R.id.movie_overview)).check(matches(withSubstring("Natasha Romanoff")))
        onView(withId(R.id.movie_poster)).check(matches(withImage(R.drawable.adventure)))

    }

    @Test
    fun testLinksIntent(){
        Intents.init()
        //val pokreniDetalje: Intent = Intent(MovieDetailActivity::javaClass.name)
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Black Widow")
        val scenario = launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_website)).perform(click())
        intended(hasAction(Intent.ACTION_VIEW))
        Intents.release()
    }

    @Test
    fun testPozicija(){
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Black Widow")
        val scenario = launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_backdrop)).check(isAbove(withId(R.id.backdrop_guideline)))
        onView(withId(R.id.movie_poster)).check(isLeftOf(withId(R.id.movie_title)))
        onView(withId(R.id.movie_poster_card)).check(isAbove(withId(R.id.movie_overview)))
        onView(withId(R.id.movie_overview)).check(isBelow(withId(R.id.movie_poster_card)))
        onView(withId(R.id.movie_release_date)).check(isBelow(withId(R.id.movie_title)))
        onView(withId(R.id.movie_release_date)).check(isLeftAlignedWith(withId(R.id.movie_title)))
    }

    @Test
    fun testYoutubeIntent(){
        Intents.init()
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Black Widow")
        val scenario = launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).perform(click())
        intended(hasAction(Intent.ACTION_SEARCH))
        Intents.release()
    }
    @Test
    fun testYoutubeIntentVol2(){
        Intents.init()
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Black Widow")
        val scenario = launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).perform(click())
        intended(hasPackage("com.google.android.youtube"))
        Intents.release()
    }
}