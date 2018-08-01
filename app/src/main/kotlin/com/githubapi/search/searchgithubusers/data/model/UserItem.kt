package com.githubapi.search.searchgithubusers.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserItem(override var userId: String = "") : User, Comparable<UserItem>, Parcelable {
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
    override var score: Double = -1.0

    init {
        initValue()
    }

    override fun compareTo(other: UserItem): Int {
        return login.toLowerCase().compareTo(other.login.toLowerCase())
    }

    constructor(parcel: Parcel) : this(parcel.readString()) {
        login = parcel.readString()
        isFavorite = parcel.readByte() != 0.toByte()
        id = parcel.readInt()
        nodeId = parcel.readString()
        avatarUrl = parcel.readString()
        url = parcel.readString()
        htmlUrl = parcel.readString()
        followersUrl = parcel.readString()
        followingUrl = parcel.readString()
        gistsUrl = parcel.readString()
        starredUrl = parcel.readString()
        subscriptionsUrl = parcel.readString()
        organizationsUrl = parcel.readString()
        reposUrl = parcel.readString()
        eventsUrl = parcel.readString()
        receivedEventsUrl = parcel.readString()
        type = parcel.readString()
        siteAdmin = parcel.readByte() != 0.toByte()
        score = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(login)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeInt(id)
        parcel.writeString(nodeId)
        parcel.writeString(avatarUrl)
        parcel.writeString(url)
        parcel.writeString(htmlUrl)
        parcel.writeString(followersUrl)
        parcel.writeString(followingUrl)
        parcel.writeString(gistsUrl)
        parcel.writeString(starredUrl)
        parcel.writeString(subscriptionsUrl)
        parcel.writeString(organizationsUrl)
        parcel.writeString(reposUrl)
        parcel.writeString(eventsUrl)
        parcel.writeString(receivedEventsUrl)
        parcel.writeString(type)
        parcel.writeByte(if (siteAdmin) 1 else 0)
        parcel.writeDouble(score)
    }

    override fun describeContents(): Int = id

    companion object CREATOR : Parcelable.Creator<UserItem> {
        override fun createFromParcel(parcel: Parcel): UserItem {
            return UserItem(parcel)
        }

        override fun newArray(size: Int): Array<UserItem?> {
            return arrayOfNulls(size)
        }
    }
}