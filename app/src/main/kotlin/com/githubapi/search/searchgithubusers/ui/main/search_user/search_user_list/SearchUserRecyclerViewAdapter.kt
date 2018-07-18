package com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list

import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.base.BaseRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.base.BaseViewHolder
import com.githubapi.search.searchgithubusers.data.model.Item
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel

class SearchUserRecyclerViewAdapter(private val viewModel: SearchUsersViewModel,
                                    private val viewHolderFactories
                                    : Map<Int, @JvmSuppressWildcards SearchUserBaseRecyclerViewHolderFactory>)
    : BaseRecyclerViewAdapter<BaseViewHolder<Item>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item>
    = viewHolderFactories[viewType]?.createSearchUserViewHolder(parent) ?: SearchUserRecyclerViewHolder(parent, viewModel)

    override fun getItemCount(): Int = viewModel.searchedUserList.size

    override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) {

        holder.onBindViewHolder(viewModel.searchedUserList[position], position)
    }

}