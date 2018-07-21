package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.databinding.FragmentSearchUsersBinding
import com.githubapi.search.searchgithubusers.ui.main.MainViewEvent
import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_users.*
import javax.inject.Inject

class SearchUsersFragment: BaseDataBindingFragment<FragmentSearchUsersBinding>() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModel: SearchUsersViewModel

    @Inject
    lateinit var adapter: SearchUserRecyclerViewAdapter

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
        disposables.add(viewModel.searchUsersViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveSearchUsersViewEvent))
        disposables.add(mainViewModel.mainViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveMainViewEvent))
    }

    private fun unbind() {
        disposables.clear()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        searchUsers_rvUsers.adapter = adapter
        searchUsers_rvUsers.setHasFixedSize(true)
    }


    private fun receiveMainViewEvent(viewEvent: Pair<MainViewEvent, Any>) {
        when (viewEvent.first) {
            MainViewEvent.DELETE_ONE_ITEM -> if (viewEvent.second is Int) refreshDeletedOneItem(viewEvent.second as Int)
            else -> { }
        }
    }

    private fun receiveSearchUsersViewEvent(viewEvent: Pair<SearchUsersViewEvent, Any>) {
        when (viewEvent.first) {
            SearchUsersViewEvent.REFRESH_USER_LIST -> refreshUserList()
            SearchUsersViewEvent.CHECK_FAVORITE_USER -> if (viewEvent.second is Int) checkFavoriteUser(viewEvent.second as Int)
            SearchUsersViewEvent.HIDE_KEYBOARD -> hideKeyboard()
            else -> { }
        }
    }

    override fun refresh() {
        refreshUserList()
    }

    private fun refreshUserList() {
        adapter.notifyDataSetChanged()
    }

    private fun refreshDeletedOneItem(position: Int) {
        viewModel.searchedUserList.forEachIndexed { index, user ->
            if (user.userId == mainViewModel.userList[position].userId) {
                user.isFavorite = false
                adapter.notifyItemChanged(index)
                return
            }
        }

    }

    private fun checkFavoriteUser(position: Int) {
        viewModel.searchedUserList[position].isFavorite = !viewModel.searchedUserList[position].isFavorite
        adapter.notifyItemChanged(position)

        if (viewModel.searchedUserList[position].isFavorite)
            mainViewModel.addFavoriteUser(viewModel.searchedUserList[position])
        else
            mainViewModel.deleteOneItem(position, viewModel.searchedUserList[position])

    }


    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchUsers_etUserName.windowToken, 0)
    }

}