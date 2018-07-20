package com.githubapi.search.searchgithubusers.di.data

import android.content.Context
import com.githubapi.search.searchgithubusers.data.repository.RealmDatabase
import com.githubapi.search.searchgithubusers.data.repository.UserRepository
import com.githubapi.search.searchgithubusers.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRealmDatabase(@Named("appContext") context: Context): RealmDatabase = RealmDatabase(context).apply { setup() }

    @Provides
    @Singleton
    fun provideRepositoryImpl(realmDatabase: RealmDatabase): UserRepository = UserRepositoryImpl(realmDatabase)
}