package com.noviherlambang.submission3githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noviherlambang.submission3githubuser.api.RetrofitClient
import com.noviherlambang.submission3githubuser.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel:ViewModel() {
    val listFollowing=MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username:String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object :Callback<ArrayList<User>>{
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Fail",t.message.toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

            })
    }

    fun getListFollowing():LiveData<ArrayList<User>>{
        return listFollowing
    }
}