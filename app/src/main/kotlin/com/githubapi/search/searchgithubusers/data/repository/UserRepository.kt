package com.githubapi.search.searchgithubusers.data.repository

import com.githubapi.search.searchgithubusers.data.model.UserItem
import io.reactivex.Observable

interface UserRepository {
    fun getAllUsers(): Observable<UserItem>

    fun addUserItem(userItem: UserItem): Boolean

    fun deleteUserItem(id: Int)
}