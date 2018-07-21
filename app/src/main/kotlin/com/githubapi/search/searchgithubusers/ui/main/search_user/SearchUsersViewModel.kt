package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.databinding.ObservableField
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.api.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.extensions.toUserItem
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*

class SearchUsersViewModel(private val searchUsersApi: GithubSearchUserApi, private val userRepository: UserRepository) {

    val userName = ObservableField<String>()

    val searchUsersViewEventSender = PublishSubject.create<Pair<SearchUsersViewEvent, Any>>().apply { subscribeOn(Schedulers.io()) }

    val searchedUserList = ArrayList<UserItem>()

    fun sendUsersViewEvent(viewEvent: SearchUsersViewEvent, data: Any) {
        searchUsersViewEventSender.onNext(viewEvent to data)
    }

    fun searchUsers() {
        sendUsersViewEvent(SearchUsersViewEvent.HIDE_KEYBOARD, 0)

        userName.get()?.let {
            searchUsersApi.searchUsers(it)
                    .subscribe({
                        println("subscribe, ${it.total_count}, ${it.incomplete_results}")

                        it.items.forEach {
                            println("${it.login}")
                            it.userId = UUID.randomUUID().toString()
                            it.isFavorite = userRepository.getUserWithIdName(it.id, it.login)
                            searchedUserList.add(it.toUserItem())
                        }

                    }, {
                        it.printStackTrace()
                        println("Get users error: ${it.message}")
                    }, {
                        println("Get users results finished")
                        sendUsersViewEvent(SearchUsersViewEvent.REFRESH_USER_LIST, 0)
                    })
        }
    }
}