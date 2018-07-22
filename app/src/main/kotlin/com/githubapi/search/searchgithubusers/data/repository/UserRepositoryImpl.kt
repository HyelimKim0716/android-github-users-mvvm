package com.githubapi.search.searchgithubusers.data.repository

import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.RealmUserItem
import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.extensions.toRealmUserItem
import com.githubapi.search.searchgithubusers.extensions.toUserItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.realm.Sort
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import java.util.*

class UserRepositoryImpl(private val realmDatabase: RealmDatabase) : UserRepository {

    override var realmCompleteCheckSubject: PublishSubject<Any>? = null

    fun Completable.subscribeRealmCompleteCheckSubject() {
        subscribe({
            realmCompleteCheckSubject?.onNext(0)
        }, {
            realmCompleteCheckSubject?.onError(Exception())
        })
    }

    override fun getAllUsers(): Observable<UserItem>
    = Observable.fromIterable(realmDatabase.findAll(RealmUserItem::class.java, User::login.name, Sort.ASCENDING))
            .map {
                LogMgr.d("getAllUsers, ${it.userId}, ${it.id}, ${it.login}")
                it.toUserItem()
            }

    override fun getAllUsersForTest() {
        getAllUsers().subscribe({
            realmCompleteCheckSubject?.onNext(it)
        }, {
            realmCompleteCheckSubject?.onError(it)
        })
    }

    override fun getUserWithIdName(id: Int, userName: String): Boolean
        = realmDatabase.getItem(RealmUserItem::class.java,
            arrayOf(User::id.name to id, User::login.name to userName)) != null

    override fun getUsersWithName(userName: String): List<UserItem>
    = realmDatabase.getContainsFieldValueItems(RealmUserItem::class.java, User::login.name, userName, User::login.name, Sort.ASCENDING)
            .map {
                it.toUserItem()
            }

    override fun addUserItem(userItem: UserItem): Boolean {
        LogMgr.d("userItem: ${userItem.userId}, id: ${userItem.id}, ${userItem.login}")
        realmDatabase.getItem(RealmUserItem::class.java, User::userId.name, userItem.userId)
                ?.let {
                    realmCompleteCheckSubject?.onError(Exception())
                    LogMgr.e("realm has this item already")
                    return false
                }

        realmDatabase.addItem(userItem.toRealmUserItem()).subscribe({
            realmCompleteCheckSubject?.onNext(it)
        }, {
            it.printStackTrace()

            if (it is RealmPrimaryKeyConstraintException) {
                userItem.userId = UUID.randomUUID().toString()
                realmDatabase.addItem(userItem.toRealmUserItem())
            }

            realmCompleteCheckSubject?.onError(it)
        })

        return true
    }

    override fun deleteAllUsers() {
        realmDatabase.deleteAllItems(RealmUserItem::class.java).subscribeRealmCompleteCheckSubject()
    }

    override fun deleteUserItem(userId: String)
    = realmDatabase.deleteItem(RealmUserItem::class.java, User::userId.name, userId)

    override fun getUserRepositoryCount(): Long
    = realmDatabase.getCount(RealmUserItem::class.java)
}