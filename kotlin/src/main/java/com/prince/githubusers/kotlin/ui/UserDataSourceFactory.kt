package com.prince.githubusers.kotlin.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.prince.githubusers.kotlin.model.User
import javax.inject.Inject


class UserDataSourceFactory @Inject constructor(private val userDataSource: UserDataSource) : DataSource.Factory<Int, User>() {

    private val userDataSourceLiveData = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        userDataSourceLiveData.postValue(userDataSource)
        return userDataSource
    }


    fun getDataFactoryLiveData(): MutableLiveData<UserDataSource> {
        return userDataSourceLiveData;
    }
}