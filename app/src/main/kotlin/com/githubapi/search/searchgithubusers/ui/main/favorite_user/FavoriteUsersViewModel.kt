package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import android.databinding.ObservableField
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class FavoriteUsersViewModel(private val userRepository: UserRepository) {

    val userName = ObservableField<String>()

    val searchedFavoriteUserList = ArrayList<UserItem>()

    val favoriteUsersViewEventSender = PublishSubject.create<Pair<FavoriteUsersViewEvent, Any>>()
                                                    .apply { subscribeOn(Schedulers.io()) }

    fun sendFavoriteUsersViewEvent(event: FavoriteUsersViewEvent, data: Any) {
        favoriteUsersViewEventSender.onNext(event to data)
    }

    fun searchFavoriteUsers() {
        sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.HIDE_KEYBOARD, 0)

        searchedFavoriteUserList.clear()
        userName.get()?.let {

            userRepository.getUsersWithName(it).forEach {
                searchedFavoriteUserList.add(it)
            }

            sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.REFRESH_SEARCHED_USER_LIST, 0)

        } ?: sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.GET_ALL_USERS, 0)
    }

}