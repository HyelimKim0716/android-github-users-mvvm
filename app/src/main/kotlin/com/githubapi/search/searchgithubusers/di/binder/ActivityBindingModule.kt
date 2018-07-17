package com.githubapi.search.searchgithubusers.di.binder

import com.githubapi.search.searchgithubusers.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {


    @ContributesAndroidInjector
    abstract fun bindingMainActivity(): MainActivity




}