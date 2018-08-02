package com.githubapi.search.searchgithubusers.ui.detail.list.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingViewHolder
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.databinding.ItemDetailFollowerFollowingListBinding
import com.google.auto.factory.AutoFactory
import kotlinx.android.synthetic.main.item_detail_follower_following_list.view.*

@AutoFactory(implementing = [(DetailFollowBaseRecyclerViewHolderFactory::class)])
class DetailFollowRecyclerViewHolder(parent: ViewGroup)
    : BaseDataBindingViewHolder<ItemDetailFollowerFollowingListBinding, UserItem>(parent,
        LayoutInflater.from(parent.context).inflate(R.layout.item_detail_follower_following_list, parent, false)) {

    override fun onBindViewHolder(item: UserItem, position: Int) {
        binding?.item = item

        Glide.with(context)
                .load(item.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.itemDetailFollowerFollowingList_ivUser)
    }

}