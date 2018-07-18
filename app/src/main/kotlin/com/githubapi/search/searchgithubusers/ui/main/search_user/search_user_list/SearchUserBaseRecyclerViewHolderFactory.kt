package com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list

import android.view.ViewGroup

interface SearchUserBaseRecyclerViewHolderFactory {
    fun createSearchUserViewHolder(parent: ViewGroup): SearchUserRecyclerViewHolder
}