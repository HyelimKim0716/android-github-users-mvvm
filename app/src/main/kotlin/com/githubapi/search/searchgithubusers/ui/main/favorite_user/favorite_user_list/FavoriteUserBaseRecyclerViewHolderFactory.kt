package com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list

import android.view.ViewGroup

interface FavoriteUserBaseRecyclerViewHolderFactory {
    fun createFavoriteUserViewHolder(parent: ViewGroup): FavoriteUserRecyclerViewHolder
}