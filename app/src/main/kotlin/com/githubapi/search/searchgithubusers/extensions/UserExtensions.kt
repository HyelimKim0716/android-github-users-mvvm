package com.githubapi.search.searchgithubusers.extensions

import com.githubapi.search.searchgithubusers.data.model.RealmUserItem
import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.UserItem

fun <T: User> User.convert(item: T): T
        = item.also {
    it.userId = userId
    it.isFavorite = isFavorite
    it.login = login
    it.id = id
    it.nodeId = nodeId
    it.avatarUrl = avatarUrl
    it.url = url
    it.htmlUrl = htmlUrl
    it.followersUrl = followersUrl
    it.followingUrl = followingUrl
    it.gistsUrl = gistsUrl
    it.starredUrl = starredUrl
    it.subscriptionsUrl = subscriptionsUrl
    it.organizationsUrl = organizationsUrl
    it.reposUrl = reposUrl
    it.receivedEventsUrl = receivedEventsUrl
    it.type = type
    it.siteAdmin = siteAdmin
    it.score = score
}

fun User.toRealmUserItem() = this.convert(RealmUserItem())

fun User.toUserItem() = this.convert(UserItem())