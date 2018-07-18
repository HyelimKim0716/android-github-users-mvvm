package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.os.Bundle
import android.view.View
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.data.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.databinding.FragmentFavoriteUsersBinding
import javax.inject.Inject

class SearchUsersFragment: BaseDataBindingFragment<FragmentFavoriteUsersBinding>() {

    override val TAG: String? = this::class.simpleName

    override val layoutId: Int = R.layout.fragment_favorite_users

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}