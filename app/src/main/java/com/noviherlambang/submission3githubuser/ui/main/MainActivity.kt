package com.noviherlambang.submission3githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.data.model.User
import com.noviherlambang.submission3githubuser.databinding.ActivityMainBinding
import com.noviherlambang.submission3githubuser.ui.adapter.UserAdapter
import com.noviherlambang.submission3githubuser.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_HTML, data.html_url)
                    startActivity(it)
                }
            }

        })
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.apply {

            rv_user.layoutManager = LinearLayoutManager(this@MainActivity)
            rv_user.setHasFixedSize(true)
            rv_user.adapter = userAdapter

            btnSerach.setOnClickListener {
                userSearch()
            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    userSearch()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        mainViewModel.getSearchUser().observe(this, Observer {
            if (it != null) {
                userAdapter.setList(it)
                loadingShow(false)
                tv_main.visibility = View.GONE
            }
        })

        btnFavorite.setOnClickListener {
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        }
        btnSetting.setOnClickListener {
            startActivity(Intent(applicationContext, SettingActivity::class.java))
        }
    }

    private fun userSearch() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            loadingShow(true)
            mainViewModel.setSearchUsers(query)
        }
    }

    private fun loadingShow(state: Boolean) {
        if (state) {
            binding.progresBar.visibility = View.VISIBLE
            tv_main.visibility = View.GONE
        } else {
            binding.progresBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}
