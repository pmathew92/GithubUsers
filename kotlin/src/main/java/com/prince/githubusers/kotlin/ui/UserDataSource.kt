package com.prince.githubusers.kotlin.ui

import androidx.paging.ItemKeyedDataSource
import com.prince.githubusers.kotlin.data.remote.ApiService
import com.prince.githubusers.kotlin.model.User
import timber.log.Timber
import javax.inject.Inject

class UserDataSource @Inject constructor(val apiService: ApiService) : ItemKeyedDataSource<Int, User>() {
    override fun getKey(item: User): Int {
        return item.id
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<User>) {
        apiService.getUsers(0)
                .subscribe({ response ->
                    callback.onResult(response, 0, response.size)
                }, { error ->
                    Timber.e(error, "Error Initial")
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<User>) {
        apiService.getUsers(params.key)
                .subscribe({ response ->
                    callback.onResult(response)
                }, { error ->
                    Timber.e(error, "Error After ")
                }
                )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<User>) {
    }
}