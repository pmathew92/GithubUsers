package com.prince.githubusers.kotlin.data.repository

import com.prince.githubusers.kotlin.data.remote.ApiService
import com.prince.githubusers.kotlin.model.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val apiService: ApiService) {

    fun getUsers(userId: Int): Single<List<User>> {
        return apiService.getUsers(userId)
                .subscribeOn(Schedulers.io())
    }
}