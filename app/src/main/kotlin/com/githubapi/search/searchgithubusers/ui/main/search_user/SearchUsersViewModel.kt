package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.databinding.ObservableField
import com.githubapi.search.searchgithubusers.data.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.data.model.Item
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SearchUsersViewModel(val searchUsersApi: GithubSearchUserApi) {

    val userName = ObservableField<String>()

    val searchUsersViewEventSender = PublishSubject.create<Pair<SearchUsersViewEvent, Any>>().apply { subscribeOn(Schedulers.io()) }


    val searchedUserList = ArrayList<Item>()

    fun sendUsersViewEvent(viewEvent: SearchUsersViewEvent, data: Any) {
        searchUsersViewEventSender.onNext(viewEvent to data)
    }

    fun searchUsers() {
        println("searchUsers clicked, userName = ${userName.get()}")

        sendUsersViewEvent(SearchUsersViewEvent.HIDE_KEYBOARD, 0)

        userName.get()?.let {
            searchUsersApi.searchUsers(it)
                    .subscribe({
                        println("subscribe, ${it.total_count}, ${it.incomplete_results}")

                        it.items.forEach { println("${it.login}") }

                        searchedUserList.addAll(it.items)


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