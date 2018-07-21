package com.githubapi.search.searchgithubusers.data.repository

import com.githubapi.search.searchgithubusers.data.model.UserItem
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {

    fun getAllUsers(): Observable<UserItem>

    fun getUser(id: Int, userName: String): Boolean

    fun addUserItem(userItem: UserItem): Boolean

    fun deleteUserItem(userId: String): Completable
}