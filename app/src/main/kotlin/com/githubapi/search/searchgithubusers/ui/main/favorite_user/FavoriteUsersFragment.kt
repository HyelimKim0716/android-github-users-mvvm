package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.databinding.FragmentFavoriteUsersBinding
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_favorite_users.*
import kotlinx.android.synthetic.main.fragment_search_users.*
import javax.inject.Inject

class FavoriteUsersFragment: BaseDataBindingFragment<FragmentFavoriteUsersBinding>() {

    @Inject
    lateinit var viewModel: FavoriteUsersViewModel

    @Inject
    lateinit var adapter: FavoriteUserRecyclerViewAdapter

    override val TAG: String ?= this::class.simpleName

    override val layoutId: Int = R.layout.fragment_favorite_users

    val disposables = CompositeDisposable()

    override fun onResume() {
        LogMgr.d()
        bind()
        viewModel.getAllFavoriteUsers()

        super.onResume()
    }

    override fun onPause() {
        LogMgr.d()

        unbind()
        super.onPause()
    }

    private fun bind() {
        disposables.add(viewModel.favoriteUsersViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveViewEvent))
    }

    private fun unbind() {
        disposables.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogMgr.d()

        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        favoriteUsers_rvFavoriteUsers.layoutManager = GridLayoutManager(context, 3)
        favoriteUsers_rvFavoriteUsers.adapter = adapter

        viewModel.getAllFavoriteUsers()

    }

    override fun refresh() {
        viewModel.getAllFavoriteUsers()
    }

    private fun receiveViewEvent(viewEvent: Pair<FavoriteUsersViewEvent, Any>) {
        when (viewEvent.first) {
            FavoriteUsersViewEvent.REFRESH_VIEW -> refreshFavoriteUsers()
            FavoriteUsersViewEvent.REFRESH_ONE_ITEM -> if (viewEvent.second is Int) refreshOneItem(viewEvent.second as Int)
            FavoriteUsersViewEvent.DELETE_ONE_ITEM -> if (viewEvent.second is Int) deleteOneItem(viewEvent.second as Int)
        }
    }

    private fun refreshOneItem(position: Int) {
        adapter.notifyItemRemoved(position)
    }

    private fun refreshFavoriteUsers() {
        adapter.notifyDataSetChanged()
    }

    private fun deleteOneItem(position: Int) {
        viewModel.deleteOneItem(position)
    }
}