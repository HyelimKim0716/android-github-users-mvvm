package com.githubapi.search.searchgithubusers

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.githubapi.search.searchgithubusers.ui.main.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var context: Context


    @Before
    fun setup() {
        context = InstrumentationRegistry.getTargetContext().applicationContext
        assertThat(activityTestRule.activity, notNullValue())
    }

    @Test
    fun swipeViewPager() {
        onView(withId(R.id.main_viewPager))
                .check(matches(isDisplayed()))

        onView(withId(R.id.main_viewPager))
                .perform(swipeLeft())
    }

    @Test
    fun checkTabLayoutDisplayed() {
        onView(withId(R.id.main_tabLayout))
                .perform(click())
                .check(matches(isDisplayed()))

        onView(withText(context.resources.getString(R.string.search_favorite_users)))
                .perform(click())
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkTabSwitch() {
        val searchGithubUserTitle = context.resources.getString(R.string.search_github_users)

        onView(allOf(withText(searchGithubUserTitle), isDescendantOfA(withId(R.id.main_tabLayout))))
                .perform(click())
                .check(matches(isDisplayed()))

        assertThat(activityTestRule.activity.fragmentPagerAdapter.getPageTitle(0).toString(),
                Matchers.equalTo(searchGithubUserTitle))

        val searchFavoriteTitle = context.resources.getString(R.string.search_favorite_users)

        onView(allOf(withText(searchFavoriteTitle), isDescendantOfA(withId(R.id.main_tabLayout))))
                .perform(click())
                .check(matches(isDisplayed()))

        assertThat(activityTestRule.activity.fragmentPagerAdapter.getPageTitle(1).toString(),
                Matchers.equalTo(searchFavoriteTitle))

    }
}