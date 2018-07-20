package com.githubapi.search.searchgithubusers.data.model

import java.util.*

interface User {
    var userId: String
    var login: String
    var id: Int
    var node_id: String
    var avatar_url: String
    var url: String
    var html_url: String
    var followers_url: String
    var following_url: String
    var gists_url: String
    var starred_url: String
    var subscriptions_url: String
    var organizations_url: String
    var repos_url: String
    var events_url: String
    var received_events_url: String
    var type: String
//    val site_admin: Boolean
//    val source: Double

    var isFavorite: Boolean
    var createdTime: Long

    fun initValue() {
        userId = UUID.randomUUID().toString()
        login = ""
        id = -1
        node_id = ""
        avatar_url = ""
        url = ""
        html_url = ""
        followers_url = ""
        following_url = ""
        gists_url = ""
        starred_url = ""
        subscriptions_url = ""
        organizations_url = ""
        repos_url = ""
        events_url = ""
        received_events_url = ""
        type = ""
        isFavorite = false
        createdTime = getCurrentTime()
    }

    fun getCurrentTime() = System.currentTimeMillis()
}