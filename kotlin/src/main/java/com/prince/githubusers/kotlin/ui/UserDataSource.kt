package com.prince.githubusers.kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.prince.githubusers.kotlin.data.remote.ApiService
import com.prince.githubusers.kotlin.model.User
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UserDataSource @Inject constructor(private val apiService: ApiService) : ItemKeyedDataSource<Int, User>() {
    private var _error: MutableLiveData<Boolean> = MutableLiveData()
    private var _loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getError(): LiveData<Boolean> {
        return _error
    }


    fun getLoading(): LiveData<Boolean> {
        return _loading
    }

    override fun getKey(item: User): Int {
        return item.id
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<User>) {
        apiService.getUsers(0)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _error.postValue(false)
                    _loading.postValue(true)
                }
                .doAfterTerminate {
                    _loading.postValue(false)
                }
                .subscribe({ response ->
                    callback.onResult(response)
                }, { error ->
                    _error.postValue(true)
                    Timber.e(error, "Error Initial")
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<User>) {
        apiService.getUsers(params.key)
                .subscribeOn(Schedulers.io())
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