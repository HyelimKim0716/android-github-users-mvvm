package com.githubapi.search.searchgithubusers.di

import com.githubapi.search.searchgithubusers.data.api.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideSearchUsersViewModel(githubSearchUserApi: GithubSearchUserApi, userRepository: UserRepository) = SearchUsersViewModel(githubSearchUserApi, userRepository)

    @Provides
    @Singleton
    fun provideFavoriteUsersViewModel(userRepository: UserRepository) = FavoriteUsersViewModel(userRepository)


}