package com.githubapi.search.searchgithubusers.data.api

import com.githubapi.search.searchgithubusers.data.model.Item
import com.githubapi.search.searchgithubusers.data.model.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubSearchUserApi {

    // search/users
    @GET("search/users")
    fun searchUsers(@Query("q") q: String): Observable<Users>

}