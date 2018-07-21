package com.githubapi.search.searchgithubusers.ui.main

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
    }

    fun deleteOneItem(position: Int, userItem: UserItem) {
        userRepository.deleteUserItem(userItem.userId).subscribe({
            LogMgr.d("delete ${userItem.login} item completed")

            userList.remove(userItem)
            sendMainViewEvent(MainViewEvent.REFRESH_DELETED_ONE_ITEM, position)
        }, {
            it.printStackTrace()
            LogMgr.e("getAllFavoriteUsers Failed. Error: ${it.message}")
        })

    }

    fun getAllFavoriteUsers() {
        LogMgr.d()
        userList.clear()
        userRepository.getAllUsers().subscribe({
            userList.add(it)
            LogMgr.d("$${it.login}, ${it.login}")
        }, {
            it.printStackTrace()
            LogMgr.e("getAllFavoriteUsers Failed. Error: ${it.message}")
        }, {
            LogMgr.d("Getting all favorite users is completed, userList: ${userList.size}")
            sendMainViewEvent(MainViewEvent.REFRESH_VIEW, 0)
        })
    }


}