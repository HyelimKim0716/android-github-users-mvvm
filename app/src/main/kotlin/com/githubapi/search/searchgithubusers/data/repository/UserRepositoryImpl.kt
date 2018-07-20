package com.githubapi.search.searchgithubusers.data.repository

import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.model.RealmUserItem
import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.extensions.toRealmUserItem
import com.githubapi.search.searchgithubusers.extensions.toUserItem
import io.reactivex.Observable
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import java.util.*

class UserRepositoryImpl(private val realmDatabase: RealmDatabase) : UserRepository {

    override fun getAllUsers(): Observable<UserItem>
    = Observable.fromIterable(realmDatabase.findAll(RealmUserItem::class.java))
            .map {
                LogMgr.d("getAllUsers, ${it.userId}, ${it.id}, ${it.login}")
                it.toUserItem()
            }


    override fun getUser(id: Int, userName: String): Boolean
        = realmDatabase.getItem(RealmUserItem::class.java,
            arrayOf(User::id.name to id, User::login.name to userName)) != null

    override fun addUserItem(userItem: UserItem): Boolean {
        LogMgr.d("userItem: ${userItem.userId}, id: ${userItem.id}, ${userItem.login}")
        realmDatabase.getItem(RealmUserItem::class.java, User::userId.name, userItem.userId)
                ?.let {
                    LogMgr.d("realm has this item already")
                    return false
                }

        realmDatabase.addItem(userItem.toRealmUserItem()).subscribe({

        }, {
            it.printStackTrace()

            if (it is RealmPrimaryKeyConstraintException) {
                userItem.userId = UUID.randomUUID().toString()
                realmDatabase.addItem(userItem.toRealmUserItem())
            }
        })

        return true
    }

    override fun deleteUserItem(userId: String)
    = realmDatabase.deleteItem(RealmUserItem::class.java, User::userId.name, userId)




}