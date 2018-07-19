package com.githubapi.search.searchgithubusers.data.model

import io.realm.RealmObject

data class Users(val total_count: Int, val incomplete_results: Boolean, val items: List<Item>)

data class Item(val login: String,
                val id: Int,
                val node_id: String,
                val avatar_url: String,
                val url: String,
                val html_url: String,
                val followers_url: String,
                val following_url: String,
                val gists_url: String,
                val starred_url: String,
                val subscriptions_url: String,
                val organizations_url: String,
                val repos_url: String,
                val events_url: String,
                val received_events_url: String,
                val type: String,
                val site_admin: Boolean,
                val source: Double,
                var isFavorite: Boolean,
                var createdTime: Long): RealmObject() {

}

//data class Item(override var login: String,
//                override var id: Int,
//                override var node_id: String,
//                override var avatar_url: String,
//                override var url: String,
//                override var html_url: String,
//                override var followers_url: String,
//                override var following_url: String,
//                override var gists_url: String,
//                override var starred_url: String,
//                override var subscriptions_url: String,
//                override var organizations_url: String,
//                override var repos_url: String,
//                override var events_url: String,
//                override var received_events_url: String,
//                override var type: String,
//                val site_admin: Boolean,
//                val source: Double,
//                override var isFavorite: Boolean,
//                override var createdTime: Long) : User