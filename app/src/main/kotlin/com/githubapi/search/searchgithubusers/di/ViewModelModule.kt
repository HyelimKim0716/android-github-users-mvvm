package com.githubapi.search.searchgithubusers.di

import com.githubapi.search.searchgithubusers.data.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideSearchUsersViewModel(githubSearchUserApi: GithubSearchUserApi) = SearchUsersViewModel(githubSearchUserApi)

    @Provides
    @Singleton
    fun provideFavoriteUsersViewModel() = FavoriteUsersViewModel()


}