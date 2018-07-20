package com.githubapi.search.searchgithubusers.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmUserItem : User, RealmObject() {
    override lateinit var login: String
    @PrimaryKey override var id: Int = 0
    override var node_id: String = ""
    override var avatar_url: String = ""
    override var url: String = ""
    override var html_url: String = ""
    override var followers_url: String = ""
    override var following_url: String = ""
    override var gists_url: String = ""
    override var starred_url: String = ""
    override var subscriptions_url: String = ""
    override var organizations_url: String = ""
    override var repos_url: String = ""
    override var events_url: String = ""
    override var received_events_url: String = ""
    override var type: String = ""
    override var isFavorite: Boolean = false
    override var createdTime: Long = -1
}