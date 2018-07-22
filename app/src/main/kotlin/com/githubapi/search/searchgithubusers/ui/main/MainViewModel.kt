package com.githubapi.search.searchgithubusers.ui.main

import android.support.annotation.VisibleForTesting
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MainViewModel(private val userRepository: UserRepository) {

    val userList = ArrayList<UserItem>()

    val mainViewEventSender = PublishSubject.create<Pair<MainViewEvent, Any>>().apply { subscribeOn(Schedulers.io()) }

    fun sendMainViewEvent(viewEvent: MainViewEvent, data: Any) {
        mainViewEventSender.onNext(viewEvent to data)
    }

    fun addFavoriteUser(userItem: UserItem) {
        userRepository.addUserItem(userItem)
        userList.add(userItem)
        sendMainViewEvent(MainViewEvent.REFRESH_ADDED_ONE_USER_LIST, userItem)
    }

    fun deleteOneItem(userItem: UserItem) {

        userList.forEach {
            if (it.userId == userItem.userId)
                it.isFavorite = false
        }

        userRepository.deleteUserItem(userItem.userId).subscribe({

            userList.remove(userItem)
            sendMainViewEvent(MainViewEvent.REFRESH_DELETED_ONE_ITEM, userItem)

            userRepository.realmCompleteCheckSubject?.onNext(userItem)
        }, {
            it.printStackTrace()
            LogMgr.e("getAllFavoriteUsers Failed. Error: ${it.message}")

            userRepository.realmCompleteCheckSubject?.onError(it)
        })

    }

    fun getAllFavoriteUsers() {
        userList.clear()
        userRepository.getAllUsers().subscribe({
            userList.add(it)
        }, {
            it.printStackTrace()
            LogMgr.e("getAllFavoriteUsers Failed. Error: ${it.message}")
        }, {
            sendMainViewEvent(MainViewEvent.REFRESH_All_FAVORITE_USER_LIST, 0)
        })
    }

}

private val testUserName = "testUserName"
@VisibleForTesting
val demoUserItems = arrayListOf(
        UserItem().apply {
            id = 1
            login = "$testUserName 1"
            isFavorite = true
        },
        UserItem().apply {
            id = 2
            login = "$testUserName 2"
            isFavorite = true
        },
        UserItem().apply {
            id = 3
            login = "$testUserName 3"
            isFavorite = false
        },
        UserItem().apply {
            id = 4
            login = "$testUserName 4"
            isFavorite = false
        },
        UserItem().apply {
            id = 5
            login = "$testUserName 5"
            isFavorite = false
        }
)