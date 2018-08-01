package com.githubapi.search.searchgithubusers.ui.detail

import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.data.api.GithubSearchUserApi
import com.githubapi.search.searchgithubusers.data.model.UserItem
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DetailViewModel(private val githubSearchUserApi: GithubSearchUserApi) {

    val followerList = ArrayList<UserItem>()

    val followingList = ArrayList<UserItem>()

    val detailViewEventSender = PublishSubject.create<Pair<DetailViewEvent, Any>>().apply {
        subscribeOn(Schedulers.io())
    }

    fun sendDetailViewEvent(viewEvent: DetailViewEvent, data: Any) {
        detailViewEventSender.onNext(viewEvent to data)
    }

    fun loadUserInformation(userItem: UserItem) {
        githubSearchUserApi.getFollowers(userItem.login)
                .subscribe({ resultFollowerList ->

                    followerList.let {
                        it.clear()
                        it.addAll(resultFollowerList)
                    }

                    LogMgr.d("follower's name : $resultFollowerList")
                    sendDetailViewEvent(DetailViewEvent.REFRESH_FOLLOWER_LIST, 0)

                }, {
                    it.printStackTrace()
                    LogMgr.d("getFollowers error: ${it.message}")
                }, {
                    LogMgr.d("getFollowers completed")
                })

        githubSearchUserApi.getFollowings(userItem.login)
                .subscribe({ resultFollowingList ->
                    followingList.let {
                        it.clear()
                        it.addAll(resultFollowingList)
                    }

                    LogMgr.d("following's name : $resultFollowingList")


                    sendDetailViewEvent(DetailViewEvent.REFRESH_FOLLOWING_LIST, 0)
                }, {
                    it.printStackTrace()
                    LogMgr.d("getFollowings error: ${it.message}")
                }, {
                    LogMgr.d("getFollowings completed")
                })
    }
}