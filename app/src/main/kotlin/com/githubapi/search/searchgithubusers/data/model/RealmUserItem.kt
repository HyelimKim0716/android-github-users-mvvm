package com.githubapi.search.searchgithubusers.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmUserItem(@PrimaryKey override var userId: String = "") : User, RealmObject() {
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