package com.githubapi.search.searchgithubusers.di.binder

import com.githubapi.search.searchgithubusers.di.ui.DetailModule
import com.githubapi.search.searchgithubusers.ui.main.MainActivity
import com.githubapi.search.searchgithubusers.di.ui.MainModule
import com.githubapi.search.searchgithubusers.ui.detail.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {


    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun bindingMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(DetailModule::class)])
    abstract fun bindingDetailActivity(): DetailActivity


}