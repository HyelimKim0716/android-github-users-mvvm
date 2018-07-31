package com.githubapi.search.searchgithubusers.ui.detail

import com.githubapi.search.searchgithubusers.data.api.GithubSearchUserApi

class DetailViewModel(private val githubSearchUserApi: GithubSearchUserApi) {

    fun loadUserInformation() {
        githubSearchUserApi.searchUsers("")
    }
}