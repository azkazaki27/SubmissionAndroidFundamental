package com.azka.submissionsatuandroidfundamental.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.azka.submissionsatuandroidfundamental.data.local.entity.UserEntity
import com.azka.submissionsatuandroidfundamental.data.local.room.GithubUserDao
import com.azka.submissionsatuandroidfundamental.data.remote.response.DetailUserResponse
import com.azka.submissionsatuandroidfundamental.data.remote.response.ItemsItem
import com.azka.submissionsatuandroidfundamental.data.remote.retrofit.ApiService

class GithubUserRepository (
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao
) {
    fun getUser(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(username)
            val items = response.items
            if(items==null) emit(Result.Empty)
            else emit(Result.Success(items))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getFollowing(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            if(response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowing: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getFollowers(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            if(response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getDetailUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getAllFavorite(): LiveData<List<UserEntity>> {
        return githubUserDao.getAllFavorite()
    }
    fun isFavorite(username: String): LiveData<Boolean> {
        return githubUserDao.isFavorite(username)
    }
    suspend fun addFavorite(user: UserEntity) {
        githubUserDao.addFavorite(user)
    }
    suspend fun deleteFavorite(username: String) {
        githubUserDao.deleteFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            githubUserDao: GithubUserDao
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, githubUserDao)
            }.also { instance = it }
    }
}