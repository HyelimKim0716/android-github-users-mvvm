package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class FavoriteUsersViewModel(private val userRepository: UserRepository) {

    val favoriteUsersViewEventSender = PublishSubject.create<Pair<FavoriteUsersViewEvent, Any>>().apply { subscribeOn(Schedulers.io()) }

    val userList = ArrayList<UserItem>()

    fun sendFavoriteUsersViewEvent(event: FavoriteUsersViewEvent, data: Any) {
        favoriteUsersViewEventSender.onNext(event to data)
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
            LogMgr.d("Getting all favorite users is completed")
            sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.REFRESH_VIEW, 0)
        })
    }

    fun deleteOneItem(position: Int) {
        userRepository.deleteUserItem(userList[position].userId).subscribe({
            LogMgr.d("delete $position item completed")
            userList.removeAt(position)
            sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.REFRESH_ONE_ITEM, position)
        }, {
            it.printStackTrace()
            LogMgr.e("getAllFavoriteUsers Failed. Error: ${it.message}")
        })

    }

}