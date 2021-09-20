package com.noviherlambang.submission3githubuser.data.model

data class DetailUserResponse (
    val login:String,
    val id:Int,
    val avatar_url:String,
    val html_url:String,
    val followers_url:String,
    val following_url:String,
    val repos_url:String,
    val name:String,
    val email:String,
    val bio:String,
    val public_repos:Int,
    val followers:Int,
    val following:Int
)