package com.githubapi.search.searchgithubusers.data.repository

import com.githubapi.search.searchgithubusers.data.model.UserItem
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {

    fun getAllUsers(): Observable<UserItem>

    fun getUserWithIdName(id: Int, userName: String): Boolean

    fun getUsersWithName(userName: String): List<UserItem>

    fun addUserItem(userItem: UserItem): Boolean

    fun deleteUserItem(userId: String): Completable
}