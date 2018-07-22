package com.githubapi.search.searchgithubusers.base

import android.support.annotation.VisibleForTesting
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.di.DaggerAppComponent
import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class BaseApplication: DaggerApplication() {

    @VisibleForTesting
    @Inject
    lateinit var userRepository: UserRepository

    @VisibleForTesting
    @Inject
    lateinit var mainViewModel: MainViewModel

    @VisibleForTesting
    @Inject
    lateinit var searchGithubUsersModel: SearchUsersViewModel

    @VisibleForTesting
    @Inject
    lateinit var searchFavoriteUsersModel: FavoriteUsersViewModel

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}