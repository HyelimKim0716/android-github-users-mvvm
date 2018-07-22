package com.githubapi.search.searchgithubusers

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.githubapi.search.searchgithubusers.base.BaseApplication
import com.githubapi.search.searchgithubusers.ui.main.MainActivity
import com.githubapi.search.searchgithubusers.ui.main.demoUserItems
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteUsersFragmentTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var application: BaseApplication

    @Before
    fun setup() {
        application = InstrumentationRegistry.getTargetContext().applicationContext as BaseApplication
        RepositoryTest.application = application

        onView(withId(R.id.main_viewPager))
                .perform(swipeLeft())

        RepositoryTest.deleteAllFavoriteUsers()
        RepositoryTest.loadDemoUserItems()
    }

    @Test
    fun checkAllSavedFavoriteUsers() {
        demoUserItems.forEach {
            onView(allOf(withId(R.id.favoriteUserListItem_tvUserName), withText(it.login))).check(matches(isDisplayed()))
        }
    }
}