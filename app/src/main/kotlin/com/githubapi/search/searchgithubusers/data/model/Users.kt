package com.githubapi.search.searchgithubusers.data.model

data class Users(val total_count: Int, val incomplete_results: Boolean, val userItems: List<UserItem>)

data class UserItem(override var login: String = "",
                    override var id: Int = -1,
                    override var node_id: String = "",
                    override var avatar_url: String = "",
                    override var url: String = "",
                    override var html_url: String = "",
                    override var followers_url: String = "",
                    override var following_url: String = "",
                    override var gists_url: String = "",
                    override var starred_url: String = "",
                    override var subscriptions_url: String = "",
                    override var organizations_url: String = "",
                    override var repos_url: String = "",
                    override var events_url: String = "",
                    override var received_events_url: String = "",
                    override var type: String = "",
                    val site_admin: Boolean = false,
                    val source: Double = -1.0,
                    override var isFavorite: Boolean = false,
                    override var createdTime: Long = 0) : User