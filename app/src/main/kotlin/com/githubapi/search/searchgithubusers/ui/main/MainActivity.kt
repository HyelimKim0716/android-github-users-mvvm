package com.githubapi.search.searchgithubusers.ui.main

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseActivity
import com.githubapi.search.searchgithubusers.common.LogMgr
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var searchUsersFragment: SearchUsersFragment

    @Inject
    lateinit var favoriteUsersFragment: FavoriteUsersFragment

    private var toolbarLayoutParams: AppBarLayout.LayoutParams ?= null

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

}
