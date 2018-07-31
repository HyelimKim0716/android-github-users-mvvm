package com.githubapi.search.searchgithubusers.data.model

import com.githubapi.search.searchgithubusers.common.UserValueManager

interface User {
    var userId: String
    var isFavorite: Boolean
    var login: String
    var id: Int
    var nodeId: String
    var avatarUrl: String
    var url: String
    var htmlUrl: String
    var followersUrl: String
    var followingUrl: String
    var gistsUrl: String
    var starredUrl: String
    var subscriptionsUrl: String
    var organizationsUrl: String
    var reposUrl: String
    var eventsUrl: String
    var receivedEventsUrl: String
    var type: String
    var siteAdmin: Boolean
    var source: Double

    fun initValue() {
        userId = UserValueManager.createUserId()
        login = ""
        id = -1
        nodeId = ""
        avatarUrl = ""
        url = ""
        htmlUrl = ""
        followersUrl = ""
        followingUrl = ""
        gistsUrl = ""
        starredUrl = ""
        subscriptionsUrl = ""
        organizationsUrl = ""
        reposUrl = ""
        eventsUrl = ""
        receivedEventsUrl = ""
        type = ""
        isFavorite = false
    }

}