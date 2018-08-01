package com.githubapi.search.searchgithubusers.ui.detail.list.follower

import android.view.ViewGroup

interface DetailFollowerBaseRecyclerViewHolderFactory {
    fun createFollowerRecyclerViewHolder(parent: ViewGroup): DetailFollowerRecyclerViewHolder
}