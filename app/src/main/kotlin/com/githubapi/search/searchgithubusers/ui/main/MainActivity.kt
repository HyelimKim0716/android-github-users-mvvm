package com.githubapi.search.searchgithubusers.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseActivity
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersViewEvent
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersViewEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_users.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var searchUsersFragment: SearchUsersFragment

    @Inject
    lateinit var favoriteUsersFragment: FavoriteUsersFragment

    private var toolbarLayoutParams: AppBarLayout.LayoutParams ?= null

    private val disposables = CompositeDisposable()

    override fun onResume() {
        bind()
        super.onResume()
    }

    override fun onPause() {
        unbind()
        super.onPause()
    }

    private fun bind() {
        disposables.add(searchUsersFragment.searchUsersViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveSearchUsersViewEvent))
        disposables.add(favoriteUsersFragment.favoriteUsersViewEventSender.observeOn(AndroidSchedulers.mainThread()).subscribe(::receiveFavoriteUsersViewEvent))
    }

    private fun unbind() {
        disposables.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        toolbarLayoutParams = main_toolbar.layoutParams as? AppBarLayout.LayoutParams


        val fragmentPagerAdapter = MainTabFragmentPagerAdapter(supportFragmentManager).apply {
            clearFragment()
            addFragment(searchUsersFragment)
            addFragment(favoriteUsersFragment)
        }

        main_viewPager.adapter = fragmentPagerAdapter
        main_tabLayout.setupWithViewPager(main_viewPager)
        main_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                LogMgr.d("position: $position")
                fragmentPagerAdapter.getItem(position).refresh()
            }
        })


    }

    private fun receiveSearchUsersViewEvent(viewEvent: Pair<SearchUsersViewEvent, Any>) {
        when (viewEvent.first) {
            SearchUsersViewEvent.RECYCLER_VIEW_SCROLL_UP -> visibleTopWidgets(View.VISIBLE)
            SearchUsersViewEvent.RECYCLER_VIEW_SCROLL_DOWN -> visibleTopWidgets(View.GONE)
        }
    }

    private fun receiveFavoriteUsersViewEvent(viewEvent: Pair<FavoriteUsersViewEvent, Any>) {
        when (viewEvent.first) {
            FavoriteUsersViewEvent.RECYCLER_VIEW_SCROLL_UP -> visibleTopWidgets(View.VISIBLE)
            FavoriteUsersViewEvent.RECYCLER_VIEW_SCROLL_DOWN -> visibleTopWidgets(View.GONE)
        }
    }

    private fun visibleTopWidgets(visibility: Int) {
        LogMgr.d("visibility = $visibility, appBarLayout: ${main_appBarLayout.visibility}, main_tabLayout: ${main_tabLayout.visibility}")

//        if (main_appBarLayout.visibility == visibility && main_tabLayout.visibility == visibility) return

        val appBarLayoutHeight = main_appBarLayout.height.toFloat()
        val tabLayoutHeight = main_tabLayout.height.toFloat()

        when (visibility) {
            View.VISIBLE -> {
                main_appBarLayout.let {
                    it.visibility = View.VISIBLE
                    it.animate().translationY(0F).setListener(null)
                }

                main_tabLayout.let {
                    it.visibility = View.VISIBLE
                    it.animate().translationY(0F)
                }
            }

            View.GONE -> {
                main_appBarLayout.animate().translationY(-appBarLayoutHeight).setListener(animatorListenerGoneAdapter)
                main_tabLayout.animate().translationY(- appBarLayoutHeight - tabLayoutHeight)
            }
        }
    }

    private val animatorListenerGoneAdapter = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            main_appBarLayout.visibility = View.GONE
            main_tabLayout.visibility = View.GONE
        }
    }

}
