package com.githubapi.search.searchgithubusers.ui.detail

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapi.search.searchgithubusers.R

import com.githubapi.search.searchgithubusers.base.BaseDataBindingActivity
import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.databinding.ActivityDetailBinding
import com.githubapi.search.searchgithubusers.ui.detail.list.follower.DetailFollowerRecyclerViewAdapter
import com.githubapi.search.searchgithubusers.ui.detail.list.follower.DetailFollowingRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseDataBindingActivity<ActivityDetailBinding>() {

    override val layoutId: Int = R.layout.activity_detail

    @Inject
    lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var followerListAdapter: DetailFollowerRecyclerViewAdapter

    @Inject
    lateinit var followingListAdapter: DetailFollowingRecyclerViewAdapter

    private val disposables = CompositeDisposable()

    override fun onResume() {
        bind()
        loadUserInformation()
        super.onResume()
    }

    override fun onPause() {
        unbind()
        super.onPause()
    }

    private fun bind() {
        disposables.add(viewModel.detailViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveDetailViewEvent))
    }

    private fun unbind() {
        disposables.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.item = intent.getParcelableExtra(DetailViewEvent.EXTRA_NAME_USER_ITEM.name)

        detail_rvFollower.layoutManager = GridLayoutManager(applicationContext, 3)
        detail_rvFollower.adapter = followerListAdapter

        detail_rvFollowing.layoutManager = GridLayoutManager(applicationContext, 3)
        detail_rvFollowing.adapter = followingListAdapter
    }

    private fun loadUserInformation() {
        intent.getParcelableExtra<UserItem>(DetailViewEvent.EXTRA_NAME_USER_ITEM.name)?.let {
            viewModel.loadUserInformation(it)
            detail_tvScoreValue.text = it.score.toString()
            Glide.with(applicationContext)
                    .load(it.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(detail_ivUser)
        }
    }

    private fun receiveDetailViewEvent(viewEventData: Pair<DetailViewEvent, Any>) {
        when (viewEventData.first) {
            DetailViewEvent.REFRESH_FOLLOWER_LIST -> refreshFollowerList()
            DetailViewEvent.REFRESH_FOLLOWING_LIST -> refreshFollowingList()
        }
    }

    private fun refreshFollowerList() {
        if (viewModel.followerList.isEmpty()) {
            detail_rvFollower.visibility = View.GONE
            detail_tvFollowerNoResult.visibility = View.VISIBLE
        } else {
            detail_rvFollower.visibility = View.VISIBLE
            detail_tvFollowerNoResult.visibility = View.GONE

            followerListAdapter.notifyDataSetChanged()
        }
    }

    private fun refreshFollowingList() {
        if (viewModel.followingList.isEmpty()) {
            detail_rvFollowing.visibility = View.GONE
            detail_tvFollowingNoResult.visibility = View.VISIBLE
        } else {
            detail_rvFollowing.visibility = View.VISIBLE
            detail_tvFollowingNoResult.visibility = View.GONE

            followingListAdapter.notifyDataSetChanged()
        }
    }

}
