package com.githubapi.search.searchgithubusers.ui

import com.githubapi.search.searchgithubusers.data.api.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.data.model.Item
import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.Users
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.ui.main.demoUserItems
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel
import io.reactivex.Observable
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchUsersViewModelTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var githubSearchUserApi: GithubSearchUserApi

    lateinit var viewModel: SearchUsersViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModelSetup()
    }

    private fun viewModelSetup() {

        val itemList = ArrayList<Item>()
        demoUserItems.forEach {
            val item = Item(login = "", id = -1).apply {
                userId = it.userId
                login = it.login
                id = it.id
                isFavorite = it.isFavorite
            }

            itemList.add(item)
        }

        val users = Users(demoUserItems.size, true, itemList.toList())
        val demoObservableData = Observable.just(users)

        viewModel = SearchUsersViewModel(githubSearchUserApi, userRepository)

        `when`(githubSearchUserApi.searchUsers("test")).thenReturn(demoObservableData).then {
            viewModel.searchedUserList.addAll(demoUserItems)
        }

        `when`(userRepository.getUserWithIdName(demoUserItems[0].id, demoUserItems[0].login)).thenReturn(demoUserItems[0].isFavorite)
    }

    @Test
    fun searchUsersTest() {
        val searchUserViewSender = viewModel.searchUsersViewEventSender.test()

        viewModel.userName.set("test")
        viewModel.searchUsers()

        searchUserViewSender.awaitCount(2)

        assertThat(viewModel.searchedUserList, hasSize(demoUserItems.size))
        demoUserItems.forEach {
            assertThat(viewModel.searchedUserList, hasItem(hasProperty(User::id.name, `is`(it.id))))
            assertThat(viewModel.searchedUserList, hasItem(hasProperty(User::login.name, `is`(it.login))))
        }

    }

}