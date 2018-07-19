package com.githubapi.search.searchgithubusers.data.model

//open data class User(val login: String,
//                val id: Int,
//                val node_id: String,
//                val avatar_url: String,
//                val url: String,
//                val html_url: String,
//                val followers_url: String,
//                val following_url: String,
//                val gists_url: String,
//                val starred_url: String,
//                val subscriptions_url: String,
//                val organizations_url: String,
//                val repos_url: String,
//                val events_url: String,
//                val received_events_url: String,
//                val type: String,
//                val site_admin: Boolean,
//                val source: Double) {
//    var isFavorite: Boolean = false
//}

interface User {
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
}