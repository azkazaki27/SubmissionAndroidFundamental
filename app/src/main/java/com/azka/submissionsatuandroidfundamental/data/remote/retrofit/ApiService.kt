package com.azka.submissionsatuandroidfundamental.data.remote.retrofit

import com.azka.submissionsatuandroidfundamental.data.remote.response.DetailUserResponse
import com.azka.submissionsatuandroidfundamental.data.remote.response.ItemsItem
import com.azka.submissionsatuandroidfundamental.data.remote.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("search/users")
    suspend fun getUser(
        @Query("q") username: String,
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
    ): List<ItemsItem>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
    ): List<ItemsItem>

}