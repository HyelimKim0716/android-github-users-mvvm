package com.githubapi.search.searchgithubusers.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmUserItem(@PrimaryKey override var userId: String = "") : User, RealmObject() {
    override lateinit var login: String
    override var id: Int = -1
    override lateinit var nodeId: String
    override lateinit var avatarUrl: String
    override lateinit var url: String
    override lateinit var htmlUrl: String
    override lateinit var followersUrl: String
    override lateinit var followingUrl: String
    override lateinit var gistsUrl: String
    override lateinit var starredUrl: String
    override lateinit var subscriptionsUrl: String
    override lateinit var organizationsUrl: String
    override lateinit var reposUrl: String
    override lateinit var eventsUrl: String
    override lateinit var receivedEventsUrl: String
    override lateinit var type: String
    override var siteAdmin: Boolean = false
    override var source: Double = -1.0
    override var isFavorite: Boolean = false

    init {
        initValue()
    }


}