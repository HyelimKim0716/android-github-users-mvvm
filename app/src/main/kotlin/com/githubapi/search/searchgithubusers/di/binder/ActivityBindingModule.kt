package com.githubapi.search.searchgithubusers.di.binder

import com.githubapi.search.searchgithubusers.ui.main.MainActivity
import com.githubapi.search.searchgithubusers.di.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {


    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun bindingMainActivity(): MainActivity




}