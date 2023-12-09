package com.azka.submissionsatuandroidfundamental.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.azka.submissionsatuandroidfundamental.data.GithubUserRepository
import com.azka.submissionsatuandroidfundamental.data.SettingPreferences
import com.azka.submissionsatuandroidfundamental.data.dataStore
import com.azka.submissionsatuandroidfundamental.di.Injection

class ViewModelFactory private constructor(private val pref: SettingPreferences, private val githubUserRepository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        MainViewModel::class.java -> MainViewModel(pref, githubUserRepository)
        FollowViewModel::class.java -> FollowViewModel(githubUserRepository)
        DetailViewModel::class.java -> DetailViewModel(githubUserRepository)
        SettingViewModel::class.java -> SettingViewModel(pref)
        FavoriteViewModel::class.java -> FavoriteViewModel(githubUserRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(SettingPreferences.getInstance(application.dataStore), Injection.provideRepository(application.applicationContext))
            }.also { instance = it }
    }
}