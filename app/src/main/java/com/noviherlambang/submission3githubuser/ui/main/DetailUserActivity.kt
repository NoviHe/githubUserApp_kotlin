package com.noviherlambang.submission3githubuser.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.databinding.ActivityDetailUserBinding
import com.noviherlambang.submission3githubuser.ui.adapter.SectionPagerAdapter
import com.noviherlambang.submission3githubuser.ui.viewmodel.DetailUserViewModel
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_HTML = "extra_html"

        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "novi channel"
    }


    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String? = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val htmlUrl = intent.getStringExtra(EXTRA_HTML)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        setSupportActionBar(findViewById(R.id.toolbar))
        viewModel = ViewModelProvider(
            this
        ).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, Observer {
            if (it != null) {
                binding.apply {
                    name_github.text = it.name
                    username_github.text = it.login
                    if (it.email.isNullOrEmpty()) {
                        tv_email.visibility = View.GONE
                    } else {
                        tv_email.text = it.email
                        tv_email.visibility = View.VISIBLE
                    }
                    tv_url.text = it.html_url
                    if (it.bio.isNullOrEmpty()) {
                        tv_bio.visibility = View.GONE
                    } else {
                        tv_bio.text = it.bio
                        tv_bio.visibility = View.VISIBLE
                    }
                    tvRepo.text = it.public_repos.toString() + " Repository"
                    tvFollow.text =
                        it.followers.toString() + " Followers . " + it.following.toString() + " Following "
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .into(img_avatar)

                    linear.visibility = View.VISIBLE
                    toggleFavorite.visibility = View.VISIBLE
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    viewModel.addToFavorite(username, id, avatarUrl, htmlUrl)
                    sendNotif(username)
                }
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            view_pager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(view_pager)
        }
    }

    fun sendNotif(username: String) {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_white)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_notifications_white
                )
            )
            .setContentTitle(resources.getString(R.string.content_title))
            .setContentText(resources.getString(R.string.content_text))
            .setSubText(username)
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
            val notification = mBuilder.build()
            mNotificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}