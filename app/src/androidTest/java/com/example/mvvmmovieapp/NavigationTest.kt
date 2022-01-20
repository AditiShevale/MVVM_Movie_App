package com.example.mvvmmovieapp

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.mvvmmovieapp.ui.MovieListFragment
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    private lateinit var navController: NavController

    @Before
    fun setUp() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val movieListScenario = launchFragmentInContainer<MovieListFragment>(
            themeResId =
            R.style.Theme_MVVMMovieApp
        )
        movieListScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun navigate_to_detail_screen() {

        Thread.sleep(3000)
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
            )
        assertEquals(navController.currentDestination?.id, R.id.movieDetailFragment)

    }

    @Test
    fun check_title_name() {
        Thread.sleep(3000)
        onView(withText("Eternals")).check(matches(isDisplayed()))
    }
}