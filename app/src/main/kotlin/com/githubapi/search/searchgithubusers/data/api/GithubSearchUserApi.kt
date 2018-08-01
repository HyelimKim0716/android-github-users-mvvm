package com.githubapi.search.searchgithubusers.data.api

import com.githubapi.search.searchgithubusers.data.model.UserItem
import com.githubapi.search.searchgithubusers.data.model.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubSearchUserApi {

    // search/users
    @GET("search/users")
    fun searchUsers(@Query("q") q: String): Observable<Users>

    @GET("users/{user_name}/followers")
    fun getFollowers(@Path("user_name") userName: String): Observable<List<UserItem>>

    @GET("users/{user_name}/following")
    fun getFollowings(@Path("user_name") userName: String): Observable<List<UserItem>>
}