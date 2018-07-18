package com.githubapi.search.searchgithubusers.di.ui

import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewModel
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserBaseRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.main.search_user.search_user_list.SearchUserRecyclerViewHolderType
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class SearchUsersModule {

    @Provides
    fun provideUserRecyclerViewAdapter(viewModel: SearchUsersViewModel, viewHolderFactories: Map<Int, @JvmSuppressWildcards SearchUserBaseRecyclerViewHolderFactory>)
    = SearchUserRecyclerViewAdapter(viewModel, viewHolderFactories)

    @Provides
    @IntoMap
    @IntKey(SearchUserRecyclerViewHolderType.DEFALUT)
    fun provideSearchUserRecyclerViewHolder(viewModelProvider: Provider<SearchUsersViewModel>): SearchUserBaseRecyclerViewHolderFactory
    = SearchUserRecyclerViewHolderFactory(viewModelProvider)
}