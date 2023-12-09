package com.azka.submissionsatuandroidfundamental.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azka.submissionsatuandroidfundamental.data.GithubUserRepository
import com.azka.submissionsatuandroidfundamental.data.remote.response.ItemsItem
import com.azka.submissionsatuandroidfundamental.data.Result

class FollowViewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {
    private val _listFollowers = MutableLiveData<List<ItemsItem>?>()
    val listFollowers: LiveData<List<ItemsItem>?> = _listFollowers

    private val _listFollowings = MutableLiveData<List<ItemsItem>?>()
    val listFollowings: LiveData<List<ItemsItem>?> = _listFollowings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty
    fun getFollowers(username: String) {
        githubUserRepository.getFollowers(username).observeForever{ result->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }
                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _listFollowers.value = result.data
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
    fun getFollowings(username: String) {
        githubUserRepository.getFollowing(username).observeForever{ result->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }
                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _listFollowings.value = result.data
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
}