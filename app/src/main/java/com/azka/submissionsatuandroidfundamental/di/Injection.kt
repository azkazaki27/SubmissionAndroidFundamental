package com.azka.submissionsatuandroidfundamental.di

import android.content.Context
import com.azka.submissionsatuandroidfundamental.data.GithubUserRepository
import com.azka.submissionsatuandroidfundamental.data.local.room.GithubUserDatabase
import com.azka.submissionsatuandroidfundamental.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.githubUserDao()
        return GithubUserRepository.getInstance(apiService, dao)
    }
}