package com.githubapi.search.searchgithubusers.base

import dagger.android.support.DaggerFragment

abstract class BaseFragment: DaggerFragment() {
    abstract fun refresh()
}