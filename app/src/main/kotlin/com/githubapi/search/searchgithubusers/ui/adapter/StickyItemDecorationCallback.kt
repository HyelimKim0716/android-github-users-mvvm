package com.githubapi.search.searchgithubusers.ui.adapter

interface StickyItemDecorationCallback {
    fun isSection(position: Int): Boolean

    fun getSectionHeader(position: Int): CharSequence
}