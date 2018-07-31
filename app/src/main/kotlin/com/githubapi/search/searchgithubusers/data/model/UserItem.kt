package com.githubapi.search.searchgithubusers.data.model

import com.google.gson.annotations.SerializedName

data class UserItem(override var userId: String = "") : User, Comparable<UserItem> {
    override var login: String = ""
    override var isFavorite: Boolean = false
    override var id: Int = -1
    @SerializedName("node_id") override var nodeId: String  = ""
    @SerializedName("avatar_url") override var avatarUrl: String  = ""
    override var url: String  = ""
    @SerializedName("html_url") override var htmlUrl: String  = ""
    @SerializedName("followers_url") override var followersUrl: String = ""
    @SerializedName("following_url") override var followingUrl: String = ""
    @SerializedName("gists_url") override var gistsUrl: String = ""
    @SerializedName("starred_url") override var starredUrl: String = ""
    @SerializedName("subscriptions_url") override var subscriptionsUrl: String = ""
    @SerializedName("organizations_url") override var organizationsUrl: String = ""
    @SerializedName("repos_url") override var reposUrl: String = ""
    @SerializedName("events_url") override var eventsUrl: String = ""
    @SerializedName("received_events_url") override var receivedEventsUrl: String = ""
    override var type: String = ""
    @SerializedName("site_admin") override var siteAdmin: Boolean = false
    override var source: Double = -1.0

    init {
        initValue()
    }

    override fun compareTo(other: UserItem): Int {
        return login.toLowerCase().compareTo(other.login.toLowerCase())
    }
}