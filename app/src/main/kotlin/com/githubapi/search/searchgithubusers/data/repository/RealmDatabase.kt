package com.githubapi.search.searchgithubusers.data.repository

import android.content.Context
import android.util.Log
import com.githubapi.search.searchgithubusers.common.LogMgr
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.*

open class RealmDatabase(val context: Context) {
    val TAG = "RealmDatabase"

    fun setup() {
        Realm.init(context)
        RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build().let {
                    Realm.setDefaultConfiguration(it)
                }
    }

    fun getRealm() = Realm.getDefaultInstance()

    /**
     * Find all data from realm matched with clazz.
     *
     * @param clazz
     * @return RealmResults matched with clazz.
     */
    open fun <T: RealmObject> findAll(clazz: Class<T>): RealmResults<T>
            = getRealm()
            .where(clazz)
            .findAll()

    open fun <T: RealmObject> findAll(clazz: Class<T>, sortFieldName: String, sortOrder: Sort): RealmResults<T>
            = getRealm().where(clazz)
            .sort(sortFieldName, sortOrder)
            .findAll()

    /**
     * Add one item to Realm
     *
     * @param item which is created.
     * @return Single observer returns success when realm created item successfully,
     *          and throws an error when copyToRealm threw an error.
     */
    open fun <T: RealmObject> addItem(item: T): Single<T>
            = Single.create<T> { emitter ->
        getRealm().executeTransactionAsync({
            it.copyToRealm(item)
            LogMgr.d("add Item $item")
        }, {
            emitter.onSuccess(item)
            LogMgr.d("Complete add item")
        }, {
            it.printStackTrace()
            emitter.onError(it)
            LogMgr.e("addItem Error : ${it.message}")
        })
    }

    open fun <T: RealmObject> deleteItem(clazz: Class<T>, fieldName: String, value: Any): Completable
    = Completable.create { emitter ->
        getRealm().executeTransactionAsync({
            LogMgr.d("fieldName: $fieldName, value: $value")
            val item = getItem(clazz, arrayOf(fieldName to value))
            LogMgr.d("deleteItem item? $item")
            item?.deleteFromRealm() ?: emitter.onError(Exception())
        }, {
            LogMgr.d("deleteItem")
            emitter.onComplete()
        }, {
            it.printStackTrace()
            emitter.onError(it)
            LogMgr.e("deleteItem error: ${it.message}")
        })
    }

    fun <T: RealmObject> getItem(clazz: Class<T>, fieldName: String, value: Any)
    = getItem(clazz, arrayOf(fieldName to value))

    fun <T: RealmObject> getItem(clazz: Class<T>, equalToList: Array<Pair<String, Any>>): T?
    = getRealm().where(clazz).apply {

        equalToList.forEach {
                LogMgr.d("equalList: ${it.first}, ${it.second}")

            when (it.second) {
                is String -> this.equalTo(it.first, it.second as String)
                is Int -> this.equalTo(it.first, it.second as Int)
                is Long -> this.equalTo(it.first, it.second as Long)
            }
        }
    }.findFirst()
}