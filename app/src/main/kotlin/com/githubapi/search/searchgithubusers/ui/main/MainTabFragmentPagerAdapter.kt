package com.githubapi.search.searchgithubusers.ui.main

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.githubapi.search.searchgithubusers.R
import com.githubapi.search.searchgithubusers.base.BaseFragment

class MainTabFragmentPagerAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val FRAGMENT_SEARCH_USER_POSITION = 0
    private val FRAGMENT_FAVORITE_USER_POSITION = 1

    private val fragmentList = ArrayList<BaseFragment>()

    fun clearFragment() {
        fragmentList.clear()
    }

    fun addFragment(fragment: BaseFragment) {
        fragmentList.add(fragment)
    }

    override fun getItem(position: Int): BaseFragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        FRAGMENT_SEARCH_USER_POSITION -> context.resources.getString(R.string.search_github_users)
        FRAGMENT_FAVORITE_USER_POSITION -> context.resources.getString(R.string.search_favorite_users)
        else -> null
    }
}