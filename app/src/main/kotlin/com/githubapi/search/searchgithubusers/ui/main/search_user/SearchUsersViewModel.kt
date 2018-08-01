package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.databinding.ObservableField
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.common.UserValueManager
import com.githubapi.search.searchgithubusers.data.api.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.extensions.toUserItem
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.net.URLEncoder
import java.util.*
import kotlin.collections.HashMap

class SearchUsersViewModel(private val searchUsersApi: GithubSearchUserApi, private val userRepository: UserRepository) {

    val userName = ObservableField<String>()

    val searchedUserList = ArrayList<UserItem>()

    val searchUsersViewEventSender = PublishSubject.create<Pair<SearchUsersViewEvent, Any>>().apply { subscribeOn(Schedulers.io()) }

    fun sendSearchUsersViewEvent(viewEvent: SearchUsersViewEvent, data: Any) {
        searchUsersViewEventSender.onNext(viewEvent to data)
    }

    fun searchUsers() {
        sendSearchUsersViewEvent(SearchUsersViewEvent.HIDE_KEYBOARD, 0)

        searchedUserList.clear()
        userName.get()?.let {
            searchUsersApi.searchUsers("$it in:login")
                    .concatMapIterable { it.items }
                    .subscribe({
                        println("subscribe, ${it.id}, ${it.login}")

                        searchedUserList.add(it)

                        searchedUserList.sort()

                    }, {
                        it.printStackTrace()
                        LogMgr.d("Get users error: ${it.message}")
                    }, {
                        LogMgr.d("Get users results finished")
                        sendSearchUsersViewEvent(SearchUsersViewEvent.REFRESH_USER_LIST, 0)
                    })
        }
    }

    fun moveToTop() {
        sendSearchUsersViewEvent(SearchUsersViewEvent.MOVE_TO_TOP, 0)
    }
}