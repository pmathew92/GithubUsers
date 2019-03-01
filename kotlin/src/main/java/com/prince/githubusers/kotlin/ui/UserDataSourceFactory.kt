package com.prince.githubusers.kotlin.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.prince.githubusers.kotlin.model.User
import javax.inject.Inject


class UserDataSourceFactory @Inject constructor(val userDataSource: UserDataSource) : DataSource.Factory<Int, User>() {

    val userDataSourceLiveData = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        userDataSourceLiveData.value = userDataSource
        return userDataSource
    }
}