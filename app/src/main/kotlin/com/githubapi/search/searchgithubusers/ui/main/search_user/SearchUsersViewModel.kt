package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.databinding.ObservableField
import com.githubapi.search.searchgithubusers.data.GithubSearchUserApi
import javax.inject.Inject

class SearchUsersViewModel {


    @Inject
    lateinit var searchUsersApi: GithubSearchUserApi

    val userName = ObservableField<String>()

    fun searchUsers() {
        println("searchUsers clicked, userName = ${userName.get()}")

        userName.get()?.let {
            searchUsersApi.searchUsers(it)
                    .subscribe({
                        println("subscribe, ${it.total_count}, ${it.incomplete_results}")

                        it.items.forEach { println("${it.login}") }

                    }, {
                        it.printStackTrace()
                        println("Get users error: ${it.message}")
                    }, {
                        println("Get users results finished")
                    })

        }


    }
}