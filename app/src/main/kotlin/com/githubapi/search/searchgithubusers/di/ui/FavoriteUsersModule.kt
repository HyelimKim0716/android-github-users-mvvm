package com.githubapi.search.searchgithubusers.di.ui

import com.githubapi.search.searchgithubusers.ui.main.MainViewModel
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewModel
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserBaseRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.favorite_user_list.FavoriteUserRecyclerViewHolderType
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class FavoriteUsersModule {

    @Provides
    fun provideFavoriteUserRecyclerViewAdapter(viewModel: FavoriteUsersViewModel,
                                               viewHolderFactories: Map<Int, @JvmSuppressWildcards FavoriteUserBaseRecyclerViewHolderFactory>)
    = FavoriteUserRecyclerViewAdapter(viewModel, viewHolderFactories)

    @Provides
    @IntoMap
    @IntKey(FavoriteUserRecyclerViewHolderType.DEFALUT)
    fun provideFavoriteUserRecyclerViewHolder(viewModelProvider: Provider<FavoriteUsersViewModel>): FavoriteUserBaseRecyclerViewHolderFactory
            = FavoriteUserRecyclerViewHolderFactory(viewModelProvider)
}