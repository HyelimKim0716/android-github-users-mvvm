package com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list

import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.base.BaseRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.base.BaseViewHolder
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel

class SearchUserRecyclerViewAdapter(private val viewModel: SearchUsersViewModel,
                                    private val viewHolderFactories
                                    : Map<Int, @JvmSuppressWildcards SearchUserBaseRecyclerViewHolderFactory>)
    : BaseRecyclerViewAdapter<BaseViewHolder<UserItem>>() {

    var preCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserItem>
    = viewHolderFactories[viewType]?.createSearchUserViewHolder(parent) ?: SearchUserRecyclerViewHolder(parent, viewModel)

    override fun getItemCount(): Int = viewModel.searchedUserList.size

    override fun onBindViewHolder(holder: BaseViewHolder<UserItem>, position: Int) {

        holder.onBindViewHolder(viewModel.searchedUserList[position], position)
    }

}