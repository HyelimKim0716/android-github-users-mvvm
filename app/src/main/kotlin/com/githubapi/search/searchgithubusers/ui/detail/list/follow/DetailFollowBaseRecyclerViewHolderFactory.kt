package com.githubapi.search.searchgithubusers.ui.detail.list.follow

import android.view.ViewGroup

interface DetailFollowBaseRecyclerViewHolderFactory {
    fun createFollowerRecyclerViewHolder(parent: ViewGroup): DetailFollowRecyclerViewHolder
}