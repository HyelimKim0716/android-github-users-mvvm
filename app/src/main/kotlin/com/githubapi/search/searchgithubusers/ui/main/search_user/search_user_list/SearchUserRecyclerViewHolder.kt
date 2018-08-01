package com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingViewHolder
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.databinding.ItemSearchUserListBinding
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewEvent
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import kotlinx.android.synthetic.main.item_search_user_list.view.*

@AutoFactory(implementing = [(SearchUserBaseRecyclerViewHolderFactory::class)])
class SearchUserRecyclerViewHolder(parent: ViewGroup, @Provided val viewModel: SearchUsersViewModel):
        BaseDataBindingViewHolder<ItemSearchUserListBinding, UserItem>(parent,
                LayoutInflater.from(parent.context).inflate(R.layout.item_search_user_list, parent, false) ) {

    override fun onBindViewHolder(userItem: UserItem, position: Int) {
        binding?.userItem = userItem

        Glide.with(context)
                .load(userItem.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.searchUserListItem_ivUser)

        itemView.setOnClickListener { viewModel.sendSearchUsersViewEvent(SearchUsersViewEvent.CHECK_FAVORITE_USER, adapterPosition) }
        itemView.searchUserListItem_cbFavorite.setOnClickListener { viewModel.sendSearchUsersViewEvent(SearchUsersViewEvent.CHECK_FAVORITE_USER, adapterPosition) }
    }

}