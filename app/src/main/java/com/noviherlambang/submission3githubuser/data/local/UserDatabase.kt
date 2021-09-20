package com.noviherlambang.submission3githubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    companion object{
        var INSTANCE : UserDatabase?=null
        fun getDatabase(contex:Context):UserDatabase?{
            if (INSTANCE==null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(contex.applicationContext,UserDatabase::class.java,"user_databse").build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favoriteUserDao():FavoriteUserDao
}