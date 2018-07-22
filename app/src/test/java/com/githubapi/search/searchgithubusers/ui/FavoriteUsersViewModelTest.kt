package com.githubapi.search.searchgithubusers.ui

import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.ui.main.demoUserItems
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteUsersViewModelTest {
    @Mock
    lateinit var userRepository: UserRepository

    lateinit var viewModel: FavoriteUsersViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModelSetup()
    }

    private fun viewModelSetup() {
        Mockito.`when`(userRepository.getUsersWithName("test")).thenReturn(demoUserItems)

        viewModel = FavoriteUsersViewModel(userRepository)
    }

    @Test
    fun searchFavoriteUsersTest() {
        viewModel.userName.set("test")

        val searchUserViewSender = viewModel.favoriteUsersViewEventSender.test()

        viewModel.searchFavoriteUsers()

        searchUserViewSender.awaitCount(2)

        Assert.assertThat(viewModel.searchedFavoriteUserList, Matchers.hasSize(demoUserItems.size))
        demoUserItems.forEach {
            Assert.assertThat(viewModel.searchedFavoriteUserList, Matchers.hasItem(Matchers.hasProperty(User::id.name, Matchers.`is`(it.id))))
            Assert.assertThat(viewModel.searchedFavoriteUserList, Matchers.hasItem(Matchers.hasProperty(User::login.name, Matchers.`is`(it.login))))
        }

    }
}