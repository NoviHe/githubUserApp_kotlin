package com.noviherlambang.submission3githubuser.data.model

data class RepoResponse(
    val stargazers_count:Int,
    val language:String,
    val id:Int,
    val html_url:String,
    val name:String,
    val description:String,
    val updated_at:String
)