package com.githubapi.search.searchgithubusers.ui.main.favorite_user

import android.os.Bundle
import android.view.View
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseDataBindingFragment
import com.githubapi.search.searchgithubusers.databinding.FragmentFavoriteUsersBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FavoriteUsersFragment: BaseDataBindingFragment<FragmentFavoriteUsersBinding>() {

    @Inject
    lateinit var viewModel: FavoriteUsersViewModel


    override val TAG: String ?= this::class.simpleName

    override val layoutId: Int = R.layout.fragment_favorite_users

    val disposables = CompositeDisposable()

    override fun onResume() {
        bind()
        super.onResume()
    }

    override fun onPause() {
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
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel


    }

    private fun receiveViewEvent(viewEvent: Pair<FavoriteUsersViewEvent, Any>) {
        when (viewEvent.first) {
            FavoriteUsersViewEvent.REFRESH_VIEW -> refreshFavoriteUsers()
        }
    }

    private fun refreshFavoriteUsers() {

    }
}