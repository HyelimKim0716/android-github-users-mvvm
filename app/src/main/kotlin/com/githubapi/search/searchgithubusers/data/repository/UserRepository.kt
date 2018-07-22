package com.githubapi.search.searchgithubusers.data.repository

import android.support.annotation.VisibleForTesting
import com.githubapi.search.searchgithubusers.data.model.UserItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface UserRepository {

    @VisibleForTesting
    var realmCompleteCheckSubject: PublishSubject<Any>?

    fun getAllUsers(): Observable<UserItem>

    fun getAllUsersForTest()

    fun getUserWithIdName(id: Int, userName: String): Boolean

    fun getUsersWithName(userName: String): List<UserItem>

    fun addUserItem(userItem: UserItem): Boolean

    fun deleteAllUsers()

    fun deleteUserItem(userId: String): Completable

    fun getUserRepositoryCount(): Long
}