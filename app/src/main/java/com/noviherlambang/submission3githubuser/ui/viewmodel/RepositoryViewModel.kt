package com.noviherlambang.submission3githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noviherlambang.submission3githubuser.api.RetrofitClient
import com.noviherlambang.submission3githubuser.data.model.RepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel:ViewModel() {
    val listRepo=MutableLiveData<ArrayList<RepoResponse>>()

    fun setListRepository(username:String){
        RetrofitClient.apiInstance
            .getRepository(username)
            .enqueue(object :Callback<ArrayList<RepoResponse>>{
                override fun onFailure(call: Call<ArrayList<RepoResponse>>, t: Throwable) {
                    Log.d("Fail",t.message.toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<RepoResponse>>,
                    response: Response<ArrayList<RepoResponse>>
                ) {
                    if (response.isSuccessful){
                        listRepo.postValue(response.body())
                    }
                }

            })
    }

    fun getListRepository():LiveData<ArrayList<RepoResponse>>{
        return listRepo
    }
}