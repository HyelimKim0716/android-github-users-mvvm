package com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list

import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.base.BaseRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.base.BaseViewHolder
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel

class FavoriteUserRecyclerViewAdapter(private val viewModel: FavoriteUsersViewModel,
                                      private val viewHolderFactories
                                      : Map<Int, @JvmSuppressWildcards FavoriteUserBaseRecyclerViewHolderFactory>)
    : BaseRecyclerViewAdapter<BaseViewHolder<UserItem>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserItem>
    = viewHolderFactories[viewType]?.createFavoriteUserViewHolder(parent) ?: FavoriteUserRecyclerViewHolder(parent, viewModel)

    override fun getItemCount(): Int = viewModel.userList.size

    override fun onBindViewHolder(holder: BaseViewHolder<UserItem>, position: Int) {
        holder.onBindViewHolder(viewModel.userList[position], position)
    }

}