package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.os.Bundle
import android.view.View
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.data.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.databinding.FragmentFavoriteUsersBinding
import javax.inject.Inject

class SearchUsersFragment: BaseDataBindingFragment<FragmentFavoriteUsersBinding>() {
    @Inject
    lateinit var searchUsersApi: GithubSearchUserApi


    override val TAG: String? = this::class.simpleName

    override val layoutId: Int = R.layout.fragment_favorite_users

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchUsersApi.searchUsers("tom+repos:%3E42+followers:%3E1000")
                .subscribe({
                    println("subscribe, ${it.total_count}, ${it.incomplete_results}")

                    it.items.forEach { println("${it.id}") }

                }, {
                    it.printStackTrace()
                    println("Get users error: ${it.message}")
                }, {
                    println("Get users results finished")
                })

    }

}