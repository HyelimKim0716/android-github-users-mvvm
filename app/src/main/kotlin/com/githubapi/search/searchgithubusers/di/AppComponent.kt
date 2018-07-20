package com.githubapi.search.searchgithubusers.di

import android.app.Application
import com.githubapi.search.searchgithubusers.base.BaseApplication
import com.githubapi.search.searchgithubusers.di.binder.ActivityBindingModule
import com.githubapi.search.searchgithubusers.di.binder.FragmentBindingModule
import com.githubapi.search.searchgithubusers.di.data.ApiModule
import com.githubapi.search.searchgithubusers.di.data.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ViewModelModule::class,
        ApiModule::class,
        RepositoryModule::class
        ))
interface AppComponent: AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}