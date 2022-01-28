package com.example.mvvmmovieapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityScreenTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchActivity<MainActivity>()
        Intents.init()
    }
    @After
    fun tearDown() {
        Intents.release()
    }

    fun activity_screen_test(){
//        Espresso.onView(withId(R.id.red_button))
//            .perform(ViewActions.click())
//        Intents.intended(IntentMatchers.hasComponent(ComponentNameMatchers.hasShortClassName(".SecondActivity")))
    }
}