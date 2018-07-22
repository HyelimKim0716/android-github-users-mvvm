package com.githubapi.search.searchgithubusers

import android.os.SystemClock
import com.githubapi.search.searchgithubusers.base.BaseApplication
import com.githubapi.search.searchgithubusers.ui.main.demoUserItems
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import junit.framework.Assert

object RepositoryTest {
    lateinit var application: BaseApplication

    fun createRealmCompleteCheckSubjectTest(): TestObserver<Any>? {
        application.userRepository.realmCompleteCheckSubject = PublishSubject.create()
        return application.userRepository.realmCompleteCheckSubject?.test()
    }

    fun checkUserItemCount(count: Int) {
        Completable.fromCallable {
            Assert.assertEquals(count, application.userRepository.getUserRepositoryCount().toInt())
        }.subscribeOn(AndroidSchedulers.mainThread()).subscribe()

    }

    fun deleteAllFavoriteUsers() {
        val testObserver = createRealmCompleteCheckSubjectTest()

        Completable.fromCallable {
            application.userRepository.deleteAllUsers()
            application.mainViewModel.userList.clear()
        }.subscribeOn(AndroidSchedulers.mainThread()).subscribe()

        testObserver?.awaitCount(1)
        testObserver?.assertNoErrors()
        checkUserItemCount(0)
    }

    fun loadDemoUserItems() {
        val realmCompleteCheckTestObserver = createRealmCompleteCheckSubjectTest()

        Completable.fromCallable {
            demoUserItems.forEach {
                it.createdTime = System.currentTimeMillis()
                application.userRepository.addUserItem(it)

                SystemClock.sleep(100)
            }
        }.subscribeOn(AndroidSchedulers.mainThread()).subscribe()

        realmCompleteCheckTestObserver?.awaitCount(demoUserItems.size)
        realmCompleteCheckTestObserver?.assertNoErrors()

        checkUserItemCount(demoUserItems.size)
    }

}