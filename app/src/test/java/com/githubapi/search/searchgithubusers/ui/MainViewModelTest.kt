package com.githubapi.search.searchgithubusers.ui

import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.demoUserItems
import io.reactivex.Completable
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.hasProperty
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    @Mock
    lateinit var userRepository: UserRepository

    lateinit var viewModel: MainViewModel

    lateinit var invalidTestUser: UserItem

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModelSetup()
    }

    private fun viewModelSetup() {
        val demoObservableData = Observable.fromIterable(demoUserItems)

        Mockito.`when`(userRepository.getAllUsers()).thenReturn(demoObservableData)
        Mockito.`when`(userRepository.deleteUserItem(Mockito.anyString())).thenReturn(Completable.create { it.onComplete() })

        viewModel = MainViewModel(userRepository)

        invalidTestUser = UserItem().apply {
            id = -9999
            login = "invalidTestUser"
        }
    }

    @Test
    fun addFavoriteUserTest() {
        val addSenderTest = viewModel.mainViewEventSender.test()

        demoUserItems.forEach {
            viewModel.addFavoriteUser(it)
        }

        addSenderTest.awaitCount(demoUserItems.size)
        addSenderTest.assertNoErrors()
        addSenderTest.assertValueCount(demoUserItems.size)

        assertThat(viewModel.userList, hasSize(demoUserItems.size))
        demoUserItems.forEach {
            assertThat(viewModel.userList, hasItem(hasProperty(User::userId.name, `is`(it.userId))))
        }

        assertThat(viewModel.userList, not(hasItem(hasProperty(User::userId.name, `is`(invalidTestUser.userId)))))
    }

    @Test
    fun deleteFavoriteUserTest() {
        addFavoriteUserTest()

        assertThat(viewModel.userList, hasSize(demoUserItems.size))

        val deleteSenderTest = viewModel.mainViewEventSender.test()

        viewModel.deleteOneItem(demoUserItems[0])

        deleteSenderTest.awaitCount(1)
        deleteSenderTest.assertNoErrors()

        assertThat(viewModel.userList, hasSize(demoUserItems.size.minus(1)))
        assertThat(viewModel.userList, not(hasItem(hasProperty(User::userId.name, `is`(demoUserItems[0].userId)))))

        for (index in 1 until demoUserItems.size) {
            assertThat(viewModel.userList, hasItem(hasProperty(User::userId.name, `is`(demoUserItems[index].userId))))
        }

        verify(userRepository).deleteUserItem(ArgumentMatchers.anyString())
    }

    @Test
    fun getAllFavoriteUserTest() {
        val getAllSenderTest = viewModel.mainViewEventSender.test()

        viewModel.getAllFavoriteUsers()

        getAllSenderTest.awaitCount(1)
        getAllSenderTest.assertNoErrors()

        demoUserItems.forEach {
            assertThat(viewModel.userList, hasItem(hasProperty(User::userId.name, `is`(it.userId))))
            assertThat(viewModel.userList, hasItem(hasProperty(User::id.name, `is`(it.id))))
            assertThat(viewModel.userList, hasItem(hasProperty(User::login.name, `is`(it.login))))
        }

        assertThat(viewModel.userList, not(hasItem(hasProperty(User::userId.name, `is`(invalidTestUser.userId)))))
    }


}