package com.azka.submissionsatuandroidfundamental.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.azka.submissionsatuandroidfundamental.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GithubUserDatabase {
            if (INSTANCE == null) {
                synchronized(GithubUserDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GithubUserDatabase::class.java, "githubUserDatabase"
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE as GithubUserDatabase
        }
    }
}