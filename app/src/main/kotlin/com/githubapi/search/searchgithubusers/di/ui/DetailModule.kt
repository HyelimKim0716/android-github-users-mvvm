package com.githubapi.search.searchgithubusers.di.ui

import com.githubapi.search.searchgithubusers.ui.detail.DetailViewModel
import com.githubapi.search.searchgithubusers.ui.detail.list.follow.DetailFollowBaseRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.detail.list.follow.DetailFollowerRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.ui.detail.list.DetailRecyclerViewHolderType
import com.githubapi.search.searchgithubusers.ui.detail.list.follow.DetailFollowRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.detail.list.follow.DetailFollowingRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap

@Module
class DetailModule {

    @Provides
    fun provideDetailFollowerRecyclerViewAdapter(viewModel: DetailViewModel,
                                                 detailViewHolderFactories: Map<Int, @JvmSuppressWildcards DetailFollowBaseRecyclerViewHolderFactory>)
    = DetailFollowerRecyclerViewAdapter(viewModel, detailViewHolderFactories)

    @Provides
    fun provideDetailFollowingRecyclerViewAdapter(viewModel: DetailViewModel,
                                                  detailViewHolderFactories: Map<Int, @JvmSuppressWildcards DetailFollowBaseRecyclerViewHolderFactory>)
    = DetailFollowingRecyclerViewAdapter(viewModel, detailViewHolderFactories)

    @Provides
    @IntoMap
    @IntKey(DetailRecyclerViewHolderType.DEFALUT)
    fun provideDetailRecyclerViewHolder(): DetailFollowBaseRecyclerViewHolderFactory
    = DetailFollowRecyclerViewHolderFactory()
}