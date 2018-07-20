package com.githubapi.search.searchgithubusers.data.repository

import com.githubapi.search.searchgithubusers.data.model.RealmUserItem
import com.githubapi.search.searchgithubusers.data.model.User
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.extensions.toRealmUserItem
import com.githubapi.search.searchgithubusers.extensions.toUserItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import java.util.*

class UserRepositoryImpl(private val realmDatabase: RealmDatabase) : UserRepository {

    override fun getAllUsers(): Observable<UserItem>
    = Observable.fromIterable(realmDatabase.findAll(RealmUserItem::class.java))
            .map {
                it.toUserItem()
            }



    override fun addUserItem(userItem: UserItem): Boolean {
        realmDatabase.getItem(RealmUserItem::class.java, User::id.name, userItem.id)
                ?.let {

                    return false
                }

        realmDatabase.addItem(userItem.toRealmUserItem()).subscribe({

        }, {
            it.printStackTrace()

            if (it is RealmPrimaryKeyConstraintException) {
//                userItem.id = UUID.randomUUID().toString()

            }
        })

        return true
    }

    override fun deleteUserItem(id: Int) {
        realmDatabase.deleteItem(RealmUserItem::class.java, User::id.name, id)
    }


}