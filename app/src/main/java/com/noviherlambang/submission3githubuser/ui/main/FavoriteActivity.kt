package com.noviherlambang.submission3githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.data.local.FavoriteUser
import com.noviherlambang.submission3githubuser.data.model.User
import com.noviherlambang.submission3githubuser.databinding.ActivityFavoriteBinding
import com.noviherlambang.submission3githubuser.ui.adapter.UserAdapter
import com.noviherlambang.submission3githubuser.ui.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel:FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar (findViewById(R.id.toolbar))
        btnBack.setOnClickListener {
            onBackPressed()
        }

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_HTML, data.html_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager=LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = userAdapter
        }
        viewModel.getFavoriteUser()?.observe(this, Observer {
            if (it!=null){
                val list =maplist(it)
                userAdapter.setList(list)
            }
        })

    }

    private fun maplist(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }

}