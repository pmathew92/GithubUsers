package com.prince.githubusers.kotlin.data.remote

import com.prince.githubusers.kotlin.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("users")
    fun getUsers(@Query("since") userId: Int): Single<List<User>>
}