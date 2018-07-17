package com.githubapi.search.searchgithubusers.di.ui

import android.content.Context
import android.support.v4.app.FragmentManager
import com.githubapi.search.searchgithubusers.ui.main.MainTabFragmentPagerAdapter
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainModule {

//    @Provides
//    fun provideFragmentPagerAdapter(fragmentManager: FragmentManager) = MainTabFragmentPagerAdapter(fragmentManager)

    @Provides
    fun provideSearchUsersFragment() = SearchUsersFragment()

    @Provides
    fun provideFavoriteUsersFragment() = FavoriteUsersFragment()
}