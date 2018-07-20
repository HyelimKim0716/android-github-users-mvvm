package com.githubapi.search.searchgithubusers.data.model

import java.util.*

data class Users(val total_count: Int, val incomplete_results: Boolean, val items: List<Item>)

data class Item(override var userId: String = "",
                override var login: String,
                override var id: Int,
                override var node_id: String,
                override var avatar_url: String,
                override var url: String,
                override var html_url: String,
                override var followers_url: String,
                override var following_url: String,
                override var gists_url: String,
                override var starred_url: String,
                override var subscriptions_url: String,
                override var organizations_url: String,
                override var repos_url: String,
                override var events_url: String,
                override var received_events_url: String,
                override var type: String,
                val site_admin: Boolean = false,
                val source: Double = -1.0,
                override var isFavorite: Boolean = false,
                override var createdTime: Long = 0) : User {
    init {
        initValue()
    }
}

data class UserItem(override var userId: String = "") : User {
    override lateinit var login: String
    override var id: Int = -1
    override lateinit var node_id: String
    override lateinit var avatar_url: String
    override lateinit var url: String
    override lateinit var html_url: String
    override lateinit var followers_url: String
    override lateinit var following_url: String
    override lateinit var gists_url: String
    override lateinit var starred_url: String
    override lateinit var subscriptions_url: String
    override lateinit var organizations_url: String
    override lateinit var repos_url: String
    override lateinit var events_url: String
    override lateinit var received_events_url: String
    override lateinit var type: String
    override var isFavorite: Boolean = false
    override var createdTime: Long = 0

    init {
        initValue()
    }
}