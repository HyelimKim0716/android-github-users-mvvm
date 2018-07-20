package com.githubapi.search.searchgithubusers.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import com.githubapi.search.searchgithubusers.base.BaseFragment

class MainTabFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

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
        FRAGMENT_SEARCH_USER_POSITION -> "Search Users"
        FRAGMENT_FAVORITE_USER_POSITION -> "Favorite Users"
        else -> null
    }
}