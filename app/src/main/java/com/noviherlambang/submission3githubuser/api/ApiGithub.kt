package com.noviherlambang.submission3githubuser.api

import com.noviherlambang.submission3githubuser.data.model.DetailUserResponse
import com.noviherlambang.submission3githubuser.data.model.RepoResponse
import com.noviherlambang.submission3githubuser.data.model.User
import com.noviherlambang.submission3githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiGithub {
    @GET("search/users")
    @Headers("Authorization: token 1ca5094baeec5470cfa7ed7eb6d0a964dcac584a")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 1ca5094baeec5470cfa7ed7eb6d0a964dcac584a")
    fun getUserDetail(
        @Path("username") username:String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 1ca5094baeec5470cfa7ed7eb6d0a964dcac584a")
    fun getFollowers(
        @Path("username") username:String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 1ca5094baeec5470cfa7ed7eb6d0a964dcac584a")
    fun getFollowing(
        @Path("username") username:String
    ):Call<ArrayList<User>>

    @GET("users/{username}/repos")
    @Headers("Authorization: token 1ca5094baeec5470cfa7ed7eb6d0a964dcac584a")
    fun getRepository(
        @Path("username") username:String
    ):Call<ArrayList<RepoResponse>>
}