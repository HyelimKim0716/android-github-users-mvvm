package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.databinding.FragmentFavoriteUsersBinding
import com.githubapi.search.searchgithubusers.extensions.hideKeyboard
import com.githubapi.search.searchgithubusers.ui.StickyItemDecoration
import com.githubapi.search.searchgithubusers.ui.StickyItemDecorationCallback
import com.githubapi.search.searchgithubusers.ui.main.MainViewEvent
import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_favorite_users.*
import javax.inject.Inject

class FavoriteUsersFragment: BaseDataBindingFragment<FragmentFavoriteUsersBinding>() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var favoriteUsersViewModel: FavoriteUsersViewModel

    @Inject
    lateinit var adapter: FavoriteUserRecyclerViewAdapter

    override val TAG: String ?= this::class.simpleName

    override val layoutId: Int = R.layout.fragment_favorite_users

    private val disposables = CompositeDisposable()

    override fun onResume() {
        LogMgr.d()
        bind()
        mainViewModel.getAllFavoriteUsers()

        super.onResume()
    }

    override fun onPause() {
        LogMgr.d()

        unbind()
        super.onPause()
    }


    private fun bind() {
        disposables.add(favoriteUsersViewModel.favoriteUsersViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveFavoriteUsersViewEvent))
        disposables.add(mainViewModel.mainViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveMainViewEvent))

    }

    private fun unbind() {
        disposables.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogMgr.d()

        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = favoriteUsersViewModel
        favoriteUsers_rvFavoriteUsers.layoutManager = GridLayoutManager(context, 3)
        favoriteUsers_rvFavoriteUsers.adapter = adapter
    }

    override fun refresh() {
        refreshSearchedUserList()
    }

    private fun receiveMainViewEvent(viewEvent: Pair<MainViewEvent, Any>) {
        when (viewEvent.first) {
            MainViewEvent.REFRESH_All_FAVORITE_USER_LIST -> refreshAllFavoriteUsers()
            MainViewEvent.REFRESH_ADDED_ONE_USER_LIST -> if (viewEvent.second is UserItem) refreshAddedOneUserList(viewEvent.second as UserItem)
            MainViewEvent.REFRESH_DELETED_ONE_ITEM -> if (viewEvent.second is UserItem) refreshDeletedOneItem(viewEvent.second as UserItem)
            else -> {}
        }
    }

    private fun receiveFavoriteUsersViewEvent(viewEvent: Pair<FavoriteUsersViewEvent, Any>) {
        when (viewEvent.first) {
            FavoriteUsersViewEvent.GET_ALL_USERS -> refresh()
            FavoriteUsersViewEvent.REFRESH_SEARCHED_USER_LIST -> refreshSearchedUserList()
            FavoriteUsersViewEvent.DELETE_ONE_ITEM -> if (viewEvent.second is Int) deleteOneItem(viewEvent.second as Int)
            FavoriteUsersViewEvent.HIDE_KEYBOARD -> favoriteUsers_etUserName.hideKeyboard(activity)
        }
    }

    private fun refreshDeletedOneItem(deletedUserItem: UserItem) {
        var deletedPositionIndex = -1
        favoriteUsersViewModel.searchedFavoriteUserList.forEachIndexed { index, favoriteUserItem ->

            if (favoriteUserItem.userId == deletedUserItem.userId) {
                favoriteUserItem.isFavorite = false
                deletedPositionIndex = index
                return@forEachIndexed
            }
        }

        if (deletedPositionIndex >= 0) {
            favoriteUsersViewModel.searchedFavoriteUserList.removeAt(deletedPositionIndex)
            adapter.notifyItemRemoved(deletedPositionIndex)
        }

    }

    private fun refreshAllFavoriteUsers() {
        favoriteUsersViewModel.searchedFavoriteUserList.clear()
        favoriteUsersViewModel.searchedFavoriteUserList.addAll(mainViewModel.userList)

        adapter.notifyDataSetChanged()
    }

    private fun refreshAddedOneUserList(userItem: UserItem) {
        favoriteUsersViewModel.searchedFavoriteUserList.add(userItem)
        adapter.notifyItemInserted(favoriteUsersViewModel.searchedFavoriteUserList.size.minus(1))
    }

    private fun deleteOneItem(position: Int) {
        val favoriteItem = favoriteUsersViewModel.searchedFavoriteUserList[position]

        favoriteItem.isFavorite = !favoriteItem.isFavorite

        if (!favoriteItem.isFavorite)
            mainViewModel.deleteOneItem(favoriteItem)

        favoriteUsersViewModel.searchedFavoriteUserList.removeAt(position)
        adapter.notifyItemRemoved(position)

    }

    private fun refreshSearchedUserList() {
        adapter.notifyDataSetChanged()
    }

    private val decorationCallback: StickyItemDecorationCallback = object : StickyItemDecorationCallback {
        override fun isSection(position: Int): Boolean {
            return position == 0
                    || favoriteUsersViewModel
                    .searchedFavoriteUserList[position]
                    .login.toCharArray()[0].toLowerCase() != favoriteUsersViewModel
                    .searchedFavoriteUserList[position.minus(1)]
                    .login.toCharArray()[0].toLowerCase()
        }

        override fun getSectionHeader(position: Int): CharSequence {
            return favoriteUsersViewModel.searchedFavoriteUserList[position].login.subSequence(0, 1)
        }

    }
}