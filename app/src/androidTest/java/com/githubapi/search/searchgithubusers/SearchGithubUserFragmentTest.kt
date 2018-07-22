package com.githubapi.search.searchgithubusers

import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.githubapi.search.searchgithubusers.base.BaseApplication
import com.githubapi.search.searchgithubusers.ui.main.MainActivity
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchGithubUserFragmentTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var application: BaseApplication

    private lateinit var recyclerViewMatcher: RecyclerViewMatcher

    @Before
    fun setup() {
        application = InstrumentationRegistry.getTargetContext().applicationContext as BaseApplication
        RepositoryTest.application = application

        RepositoryTest.deleteAllFavoriteUsers()
        recyclerViewMatcher = RecyclerViewMatcher(R.id.searchUsers_rvUsers)
    }

    private fun createTestSearchGibhubUsersSender()
    = application.searchGithubUsersModel.searchUsersViewEventSender.test()


    @Test
    fun checkSearchGithubUsersFragment() {
        val searchGithubUserTitle = application.resources.getString(R.string.search_github_users)

        onView(allOf(withText(searchGithubUserTitle), isDescendantOfA(ViewMatchers.withId(R.id.main_tabLayout))))
                .perform(click())
    }

    @Test
    fun searchGithubUsersTest() {
        searchGithubUsers("hyelim")

        searchGithubUsers("tom")
    }

    private fun searchGithubUsers(userName: String) {
        val testSearchGibhubUsersSender = createTestSearchGibhubUsersSender()

        onView(withId(R.id.searchUsers_etUserName))
                .perform(clearText(),
                        typeText(userName),
                        closeSoftKeyboard())

        onView(withId(R.id.searchUsers_btnSearch))
                .perform(click())

        testSearchGibhubUsersSender.awaitCount(2)
        testSearchGibhubUsersSender.assertNoErrors()
    }

    @Test
    fun checkSaveFavoriteUserTest() {
        RepositoryTest.deleteAllFavoriteUsers()

        searchGithubUsers("tom")

        onView(recyclerViewMatcher.atPositionOnView(0, R.id.searchUserListItem_cbFavorite))
                .check(matches(isNotChecked()))

        onView(recyclerViewMatcher.atPositionOnView(0, R.id.searchUserListItem_cbFavorite))
                .perform(click())

        onView(recyclerViewMatcher.atPositionOnView(0, R.id.searchUserListItem_cbFavorite))
                .check(matches(isChecked()))

        val realmTest = RepositoryTest.createRealmCompleteCheckSubjectTest()

        realmTest?.awaitCount(1)
        realmTest?.assertNoErrors()

        RepositoryTest.checkUserItemCount(application.mainViewModel.userList.size)
    }

    @Test
    fun checkDeleteFavoriteUserTest() {
        RepositoryTest.deleteAllFavoriteUsers()

        searchGithubUsers("tom")

        recyclerViewMatcher = RecyclerViewMatcher(R.id.searchUsers_rvUsers)

        onView(recyclerViewMatcher.atPositionOnView(0, R.id.searchUserListItem_cbFavorite)).perform(click())

        onView(recyclerViewMatcher.atPositionOnView(0, R.id.searchUserListItem_cbFavorite)).perform(click())

        val realmTest = createTestSearchGibhubUsersSender()

        realmTest?.awaitCount(1)
        realmTest?.assertNoErrors()

        RepositoryTest.checkUserItemCount(application.mainViewModel.userList.size)

        onView(recyclerViewMatcher.atPositionOnView(0, R.id.searchUserListItem_cbFavorite))
                .check(matches(isNotChecked()))
    }

    @Test
    fun checkHideAppBarTabLayoutSearchLayout() {
        searchGithubUsers("hyelim")

        val searchedUserList = application.searchGithubUsersModel.searchedUserList
        val lastPosition = searchedUserList.size.minus(1)

        onView(withId(R.id.searchUsers_rvUsers))
                .perform(RecyclerViewActions
                        .scrollToPosition<SearchUserRecyclerViewHolder>(lastPosition.div(2)))

        SystemClock.sleep(1000)
        onView(withId(R.id.searchUsers_rvUsers))
                .perform(RecyclerViewActions
                        .scrollToPosition<SearchUserRecyclerViewHolder>(lastPosition))

        SystemClock.sleep(3000)


        onView(withId(R.id.main_appBarLayout))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.main_tabLayout))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.searchUsers_etUserName))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.searchUsers_btnSearch))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun checkShowHiddenAppBarTabLayoutSearchLayout() {
        searchGithubUsers("hyelim")

        val searchedUserList = application.searchGithubUsersModel.searchedUserList
        val lastPosition = searchedUserList.size.minus(1)

        // Scroll Down
        onView(withId(R.id.searchUsers_rvUsers))
                .perform(RecyclerViewActions
                        .scrollToPosition<SearchUserRecyclerViewHolder>(lastPosition.div(2)))

        SystemClock.sleep(1000)
        onView(withId(R.id.searchUsers_rvUsers))
                .perform(RecyclerViewActions
                        .scrollToPosition<SearchUserRecyclerViewHolder>(lastPosition))

        SystemClock.sleep(3000)


        onView(withId(R.id.main_appBarLayout))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.main_tabLayout))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.searchUsers_etUserName))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.searchUsers_btnSearch))
                .check(matches(not(isDisplayed())))


        // Scroll Up
        onView(withId(R.id.searchUsers_rvUsers))
                .perform(RecyclerViewActions
                        .scrollToPosition<SearchUserRecyclerViewHolder>(0))

        SystemClock.sleep(1000)

        onView(withId(R.id.main_appBarLayout))
                .check(matches(isDisplayed()))

        onView(withId(R.id.main_tabLayout))
                .check(matches(isDisplayed()))

        onView(withId(R.id.searchUsers_etUserName))
                .check(matches(isDisplayed()))

        onView(withId(R.id.searchUsers_btnSearch))
                .check(matches(isDisplayed()))
    }

}