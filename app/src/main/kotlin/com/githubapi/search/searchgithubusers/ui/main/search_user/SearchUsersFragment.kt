package com.githubapi.search.searchgithubusers.ui.main.search_user

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.databinding.FragmentSearchUsersBinding
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_users.*
import javax.inject.Inject

class SearchUsersFragment: BaseDataBindingFragment<FragmentSearchUsersBinding>() {

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

    private fun receiveSearchUsersViewEvent(viewEvent: Pair<SearchUsersViewEvent, Any>) {
        when (viewEvent.first) {
            SearchUsersViewEvent.REFRESH_USER_LIST -> refreshUserList()
            SearchUsersViewEvent.CHECK_FAVORITE_USER -> if (viewEvent.second is Int) checkFavoriteUser(viewEvent.second as Int)
            SearchUsersViewEvent.HIDE_KEYBOARD -> hideKeyboard()
        }
    }

    private fun refreshUserList() {
        println("refreshUserList")
        adapter.notifyDataSetChanged()
    }

    private fun checkFavoriteUser(position: Int) {
        LogMgr.d("checkFavoriteUser: position: $position, 1   isFavorite? ${viewModel.searchedUserList[position].isFavorite}")

        viewModel.searchedUserList[position].isFavorite = !viewModel.searchedUserList[position].isFavorite
        viewModel.addFavoriteUser(position)
        LogMgr.d("checkFavoriteUser: position: $position, 2   isFavorite? ${viewModel.searchedUserList[position].isFavorite}")
        adapter.notifyItemChanged(position)

    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchUsers_etUserName.windowToken, 0)
    }

}