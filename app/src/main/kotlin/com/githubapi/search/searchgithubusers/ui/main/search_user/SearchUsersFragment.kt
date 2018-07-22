package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.databinding.FragmentSearchUsersBinding
import com.githubapi.search.searchgithubusers.extensions.hideKeyboard
import com.githubapi.search.searchgithubusers.ui.adapter.StickyItemDecoration
import com.githubapi.search.searchgithubusers.ui.adapter.StickyItemDecorationCallback
import com.githubapi.search.searchgithubusers.ui.main.MainViewEvent
import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_users.*
import javax.inject.Inject

class SearchUsersFragment : BaseDataBindingFragment<FragmentSearchUsersBinding>() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var searchUsersViewModel: SearchUsersViewModel

    @Inject
    lateinit var adapter: SearchUserRecyclerViewAdapter

    @Inject
    lateinit var itemDecoration: StickyItemDecoration

    override val TAG: String? = this::class.simpleName

    override val layoutId: Int = R.layout.fragment_search_users

    private val disposables = CompositeDisposable()

    override fun onResume() {
        bind()
        super.onResume()
    }

    override fun onPause() {
        unbind()
        super.onPause()
    }

    private fun bind() {
        disposables.add(searchUsersViewModel.searchUsersViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveSearchUsersViewEvent))
        disposables.add(mainViewModel.mainViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveMainViewEvent))
    }

    private fun unbind() {
        disposables.clear()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = searchUsersViewModel
        searchUsers_etUserName.setOnEditorActionListener(editorActionListener)
        searchUsers_rvUsers.adapter = adapter

        itemDecoration.stickyItemDecorationCallback = decorationCallback
        searchUsers_rvUsers.addItemDecoration(itemDecoration)
    }

    private fun receiveMainViewEvent(viewEvent: Pair<MainViewEvent, Any>) {
        when (viewEvent.first) {
            MainViewEvent.REFRESH_DELETED_ONE_ITEM -> if (viewEvent.second is UserItem) refreshDeletedOneItem(viewEvent.second as UserItem)
            else -> { }
        }
    }

    private fun receiveSearchUsersViewEvent(viewEvent: Pair<SearchUsersViewEvent, Any>) {
        when (viewEvent.first) {
            SearchUsersViewEvent.REFRESH_USER_LIST -> refreshUserList()
            SearchUsersViewEvent.CHECK_FAVORITE_USER -> if (viewEvent.second is Int) checkFavoriteUser(viewEvent.second as Int)
            SearchUsersViewEvent.HIDE_KEYBOARD -> searchUsers_etUserName.hideKeyboard(activity)
        }
    }

    override fun refresh() {
        refreshUserList()
    }

    private fun refreshUserList() {
        adapter.notifyDataSetChanged()
    }

    private fun refreshDeletedOneItem(deletedUserItem: UserItem) {
        LogMgr.d()
        searchUsersViewModel.searchedUserList.forEachIndexed { index, searchedUserItem ->
            LogMgr.d("deleted item index = $index, deietedUserName: ${deletedUserItem.login}, ${searchedUserItem.login}, deleted: ${deletedUserItem.userId}, ${searchedUserItem.userId}, same? ${deletedUserItem.userId == searchedUserItem.userId} ")
            if (searchedUserItem.userId == deletedUserItem.userId) {
                searchedUserItem.isFavorite = false
                adapter.notifyItemChanged(index)
                return
            }
        }

    }

    private fun checkFavoriteUser(position: Int) {
        searchUsersViewModel.searchedUserList[position].isFavorite = !searchUsersViewModel.searchedUserList[position].isFavorite
        adapter.notifyItemChanged(position)

        if (searchUsersViewModel.searchedUserList[position].isFavorite)
            mainViewModel.addFavoriteUser(searchUsersViewModel.searchedUserList[position])
        else
            mainViewModel.deleteOneItem(searchUsersViewModel.searchedUserList[position])

    }


    private val editorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE)
            searchUsersViewModel.searchUsers()

        false
    }

    private val decorationCallback: StickyItemDecorationCallback = object : StickyItemDecorationCallback {
        override fun isSection(position: Int): Boolean {
            return position == 0
                    || searchUsersViewModel
                    .searchedUserList[position]
                    .login.toCharArray()[0].toLowerCase() != searchUsersViewModel
                    .searchedUserList[position.minus(1)]
                    .login.toCharArray()[0].toLowerCase()
        }

        override fun getSectionHeader(position: Int): CharSequence {
            return searchUsersViewModel.searchedUserList[position].login.subSequence(0, 1)
        }

    }
}