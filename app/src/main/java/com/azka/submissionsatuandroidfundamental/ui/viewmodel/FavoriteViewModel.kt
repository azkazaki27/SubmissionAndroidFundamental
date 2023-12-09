package com.azka.submissionsatuandroidfundamental.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azka.submissionsatuandroidfundamental.data.GithubUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {

    fun getAllFavorite() = githubUserRepository.getAllFavorite()
    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            githubUserRepository.deleteFavorite(username)
        }
    }
}