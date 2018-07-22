package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.databinding.FragmentFavoriteUsersBinding
import com.githubapi.search.searchgithubusers.extensions.hideKeyboard
import com.githubapi.search.searchgithubusers.ui.main.MainViewEvent
import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
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

    val favoriteUsersViewEventSender = PublishSubject.create<Pair<FavoriteUsersViewEvent, Any>>().apply { observeOn(AndroidSchedulers.mainThread()) }

    private lateinit var recyclerViewManager: GridLayoutManager

    private var prevFirstVisiblePosition = 0

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
        favoriteUsers_etUserName.setOnEditorActionListener(editorActionListener)
        favoriteUsers_rvFavoriteUsers.let {
            it.layoutManager = GridLayoutManager(context, 3)
            it.adapter = adapter
            it.addOnScrollListener(scrollListener)
            recyclerViewManager = it.layoutManager as GridLayoutManager
            prevFirstVisiblePosition = recyclerViewManager.findFirstCompletelyVisibleItemPosition()
        }
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
        favoriteUsersViewModel.searchedFavoriteUserList.sort()
        adapter.notifyDataSetChanged()
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

    private val editorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE)
            favoriteUsersViewModel.searchFavoriteUsers()

        false
    }

    private fun sendFavoriteUsersViewEvent(viewEvent: FavoriteUsersViewEvent, data: Any) {
        favoriteUsersViewEventSender.onNext(viewEvent to data)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val currentFirstVisiblePosition = recyclerViewManager.findFirstVisibleItemPosition()

            if (currentFirstVisiblePosition == prevFirstVisiblePosition) return

            if (currentFirstVisiblePosition - prevFirstVisiblePosition < -2)
                showSearchLayout(currentFirstVisiblePosition)
            else if (currentFirstVisiblePosition - prevFirstVisiblePosition > 2)
                hideSearchLayout(currentFirstVisiblePosition)

        }
    }

    private fun hideSearchLayout(currentFirstVisiblePosition: Int) {
        favoriteUsers_clSearch.visibility = View.GONE
        sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.RECYCLER_VIEW_SCROLL_DOWN, 0)
        prevFirstVisiblePosition = currentFirstVisiblePosition
    }

    private fun showSearchLayout(currentFirstVisiblePosition: Int) {
        favoriteUsers_clSearch.visibility = View.VISIBLE
        sendFavoriteUsersViewEvent(FavoriteUsersViewEvent.RECYCLER_VIEW_SCROLL_UP, 0)
        prevFirstVisiblePosition = currentFirstVisiblePosition
    }
}