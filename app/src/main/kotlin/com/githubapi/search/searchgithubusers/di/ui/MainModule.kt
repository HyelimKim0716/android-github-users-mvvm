package com.githubapi.search.searchgithubusers.di.ui

import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersFragment
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideSearchUsersFragment() = SearchUsersFragment()

    @Provides
    fun provideFavoriteUsersFragment() = FavoriteUsersFragment()
}