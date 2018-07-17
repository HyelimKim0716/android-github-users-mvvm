package com.githubapi.search.searchgithubusers.di.binder

import com.githubapi.search.searchgithubusers.di.ui.FavoriteUsersModule
import com.githubapi.search.searchgithubusers.di.ui.SearchUsersModule
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [(SearchUsersModule::class)])
    abstract fun provideSearchUsersFragment(): SearchUsersFragment

    @ContributesAndroidInjector(modules = [(FavoriteUsersModule::class)])
    abstract fun provideFavoriteUsersFragment(): FavoriteUsersFragment
}