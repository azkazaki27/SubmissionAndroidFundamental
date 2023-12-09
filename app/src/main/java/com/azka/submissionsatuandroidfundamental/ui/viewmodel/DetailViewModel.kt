package com.azka.submissionsatuandroidfundamental.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azka.submissionsatuandroidfundamental.data.GithubUserRepository
import com.azka.submissionsatuandroidfundamental.data.local.entity.UserEntity
import com.azka.submissionsatuandroidfundamental.data.remote.response.DetailUserResponse
import com.azka.submissionsatuandroidfundamental.data.Result
import kotlinx.coroutines.launch

class DetailViewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {
    private val _userEntity = MutableLiveData<UserEntity>()
    val userEntity: LiveData<UserEntity> = _userEntity

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun addFavorite(user: UserEntity) {
        viewModelScope.launch {
            githubUserRepository.addFavorite(user)
        }
    }
    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            githubUserRepository.deleteFavorite(username)
        }
    }
    //    fun getDetailUser(username: String) = githubUserRepository.getDetailUser(username)
    fun getDetailUser(username: String){
        githubUserRepository.getDetailUser(username).observeForever{ result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                }
                is Result.Success -> {
                    _isLoading.value = false
                    _detailUser.value = result.data
                    _userEntity.value = UserEntity(0,result.data.login,result.data.avatarUrl)
                }
                is Result.Error -> {
                    _isLoading.value = false
                }
                is Result.Empty -> {
                    _isLoading.value = false
                }
            }
        }
    }

    fun isFavorite(username: String) = githubUserRepository.isFavorite(username)
}