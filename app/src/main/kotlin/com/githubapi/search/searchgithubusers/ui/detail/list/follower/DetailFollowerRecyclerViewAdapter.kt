package com.githubapi.search.searchgithubusers.ui.detail.list.follower

import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.base.BaseRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.base.BaseViewHolder
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.ui.detail.DetailViewModel

class DetailFollowerRecyclerViewAdapter(private val viewModel: DetailViewModel,
                                        private val detailViewHolderFactories
                                  : Map<Int, @JvmSuppressWildcards DetailFollowerBaseRecyclerViewHolderFactory>)
                                  : BaseRecyclerViewAdapter<BaseViewHolder<UserItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserItem>
    = detailViewHolderFactories[viewType]?.createFollowerRecyclerViewHolder(parent) ?: DetailFollowerRecyclerViewHolder(parent)

    override fun getItemCount(): Int = viewModel.followingList.size

    override fun onBindViewHolder(holder: BaseViewHolder<UserItem>, position: Int) {
        holder.onBindViewHolder(viewModel.followingList[position], position)
    }

}