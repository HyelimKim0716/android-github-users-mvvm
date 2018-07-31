package com.githubapi.search.searchgithubusers.data.model

data class Users(val total_count: Int, val incomplete_results: Boolean, val items: List<UserItem>)