package com.githubapi.search.searchgithubusers.extensions

import com.githubapi.search.searchgithubusers.data.model.RealmUserItem
import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.UserItem

fun <T: User> User.convert(item: T): T
        = item.also {
    it.login = login
    it.id = id
    it.node_id = node_id
    it.avatar_url = avatar_url
    it.url = url
    it.html_url = html_url
    it.followers_url = followers_url
    it.following_url = following_url
    it.gists_url = gists_url
    it.starred_url = starred_url
    it.subscriptions_url = subscriptions_url
    it.organizations_url = organizations_url
    it.repos_url = repos_url
    it.received_events_url = received_events_url
    it.type = type
    it.isFavorite = isFavorite
    it.createdTime = createdTime
}

fun User.toRealmUserItem() = this.convert(RealmUserItem())

fun User.toUserItem() = this.convert(UserItem())