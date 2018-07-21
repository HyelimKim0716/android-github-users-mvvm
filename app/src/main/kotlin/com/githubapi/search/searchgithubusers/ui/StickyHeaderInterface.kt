package com.githubapi.search.searchgithubusers.ui

import android.view.View

enum class StickyHeaderViewEvent {

}

interface StickyHeaderInterface {
    fun getHeaderPositionForItem(itemPosition: Int): Int

    fun getHeaderLayout(headerPosition: Int): Int

    fun bindHeaderData(header: View, headerPosition: Int)

    fun isHeader(itemPosition: Int): Boolean

}

interface StickyItemDecorationCallback {
    fun isSection(position: Int): Boolean

    fun getSectionHeader(position: Int): CharSequence
}