package com.noviherlambang.submission3githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.data.model.Reminder
import com.noviherlambang.submission3githubuser.databinding.ActivitySettingBinding
import com.noviherlambang.submission3githubuser.preference.ReminderPreference
import com.noviherlambang.submission3githubuser.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.activity_favorite.btnBack
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySettingBinding
    private lateinit var reminder:Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        btnBack.setOnClickListener {
            onBackPressed()
        }

        tvSelectLanguage.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        tvFavoriteUser.setOnClickListener {
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        }

        val reminderPreference =ReminderPreference(this)
        if (reminderPreference.getReminder().isReminded){
            binding.switch1.isChecked = true
        } else{
            binding.switch1.isChecked = false
        }

        alarmReceiver = AlarmReceiver()

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(this,"RepeatingAlarm","09:00","Github Reminder")
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference =ReminderPreference(this)
        reminder= Reminder()

        reminder.isReminded=state
        reminderPreference.setReminder(reminder)
    }
}