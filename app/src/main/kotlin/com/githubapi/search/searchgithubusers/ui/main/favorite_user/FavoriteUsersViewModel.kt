package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class FavoriteUsersViewModel(private val userRepository: UserRepository) {

    val favoriteUsersViewEventSender = PublishSubject.create<Pair<FavoriteUsersViewEvent, Any>>().apply { subscribeOn(Schedulers.io()) }

    val userList = ArrayList<UserItem>()




}