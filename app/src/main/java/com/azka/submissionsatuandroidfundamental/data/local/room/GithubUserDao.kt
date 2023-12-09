package com.azka.submissionsatuandroidfundamental.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.azka.submissionsatuandroidfundamental.data.local.entity.UserEntity

@Dao
interface GithubUserDao {
    @Insert
    suspend fun addFavorite(user: UserEntity)

    @Query("DELETE from user where username= :username")
    suspend fun deleteFavorite(username: String)

    @Query("SELECT * from user order by id asc")
    fun getAllFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * from user where username= :username)")
    fun isFavorite(username: String): LiveData<Boolean>
}