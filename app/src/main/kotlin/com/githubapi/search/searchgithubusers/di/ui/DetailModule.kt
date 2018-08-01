package com.githubapi.search.searchgithubusers.di.ui

import com.githubapi.search.searchgithubusers.ui.detail.DetailViewModel
import com.githubapi.search.searchgithubusers.ui.detail.list.follower.DetailFollowerBaseRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.detail.list.follower.DetailFollowerRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.ui.detail.list.DetailRecyclerViewHolderType
import com.githubapi.search.searchgithubusers.ui.detail.list.follower.DetailFollowerRecyclerViewHolderFactory
import com.githubapi.search.searchgithubusers.ui.detail.list.follower.DetailFollowingRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap

@Module
class DetailModule {

    @Provides
    fun provideDetailFollowerRecyclerViewAdapter(viewModel: DetailViewModel,
                                                 detailViewHolderFactories: Map<Int, @JvmSuppressWildcards DetailFollowerBaseRecyclerViewHolderFactory>)
    = DetailFollowerRecyclerViewAdapter(viewModel, detailViewHolderFactories)

    @Provides
    fun provideDetailFollowingRecyclerViewAdapter(viewModel: DetailViewModel,
                                                  detailViewHolderFactories: Map<Int, @JvmSuppressWildcards DetailFollowerBaseRecyclerViewHolderFactory>)
    = DetailFollowingRecyclerViewAdapter(viewModel, detailViewHolderFactories)

    @Provides
    @IntoMap
    @IntKey(DetailRecyclerViewHolderType.DEFALUT)
    fun provideDetailRecyclerViewHolder(): DetailFollowerBaseRecyclerViewHolderFactory
    = DetailFollowerRecyclerViewHolderFactory()
}