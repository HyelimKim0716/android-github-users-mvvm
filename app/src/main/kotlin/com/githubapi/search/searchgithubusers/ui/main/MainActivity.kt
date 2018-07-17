package com.githubapi.search.searchgithubusers.ui.main

import android.os.Bundle
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseActivity
import com.githubapi.search.searchgithubusers.ui.main.favorite_user.FavoriteUsersFragment
import com.githubapi.search.searchgithubusers.ui.main.search_user.SearchUsersFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

//    @Inject
//    lateinit var fragmentPagerAdapter: MainTabFragmentPagerAdapter

    @Inject
    lateinit var searchUsersFragment: SearchUsersFragment

    @Inject
    lateinit var favoriteUsersFragment: FavoriteUsersFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentPagerAdapter = MainTabFragmentPagerAdapter(supportFragmentManager).apply {
            clearFragment()
            addFragment(searchUsersFragment)
            addFragment(favoriteUsersFragment)
        }

        main_viewPager.adapter = fragmentPagerAdapter
        main_tabLayout.setupWithViewPager(main_viewPager)
    }
}
