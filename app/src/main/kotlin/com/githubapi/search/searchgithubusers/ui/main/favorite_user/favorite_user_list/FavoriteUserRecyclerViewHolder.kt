package com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingViewHolder
import com.githubapi.search.searchgithubusers.base.BaseViewHolder
import com.githubapi.search.searchgithubusers.data.model.Item
import com.githubapi.search.searchgithubusers.databinding.ItemFavoriteUserListBinding
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import kotlinx.android.synthetic.main.item_search_user_list.view.*

@AutoFactory(implementing = [(FavoriteUserBaseRecyclerViewHolderFactory::class)])
class FavoriteUserRecyclerViewHolder(private val parent: ViewGroup, @Provided private val viewModel: FavoriteUsersViewModel)
    : BaseDataBindingViewHolder<ItemFavoriteUserListBinding, Item>(parent, LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_user_list, parent, false)) {

    override fun onBindViewHolder(item: Item, position: Int) {
        binding?.item = item

        Glide.with(context)
                .load(item.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.searchUserListItem_ivUser)



    }

}
