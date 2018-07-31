package com.githubapi.search.searchgithubusers.common

import java.util.*

object UserValueManager {
    fun createUserId() = UUID.randomUUID().toString()
}