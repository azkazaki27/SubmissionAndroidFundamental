package com.azka.submissionsatuandroidfundamental.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.azka.submissionsatuandroidfundamental.data.GithubUserRepository
import com.azka.submissionsatuandroidfundamental.data.SettingPreferences
import com.azka.submissionsatuandroidfundamental.data.remote.response.ItemsItem
import com.azka.submissionsatuandroidfundamental.data.Result


class MainViewModel(private val pref: SettingPreferences, private val githubUserRepository: GithubUserRepository): ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>?>()
    val listUser: LiveData<List<ItemsItem>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        getUser("gojek")
    }
    fun getUser(username: String) {
        githubUserRepository.getUser(username).observeForever{ result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }
                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _listUser.value = result.data
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                }
                is Result.Empty -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }
            }
        }
    }
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}