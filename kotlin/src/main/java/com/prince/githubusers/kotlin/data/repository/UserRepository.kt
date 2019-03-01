package com.prince.githubusers.kotlin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.prince.githubusers.kotlin.data.remote.ApiService
import com.prince.githubusers.kotlin.model.User
import com.prince.githubusers.kotlin.ui.UserDataSourceFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDataSourceFactory: UserDataSourceFactory) {


    private var _error: LiveData<Boolean> = Transformations.switchMap(userDataSourceFactory.getDataFactoryLiveData()
    ) { it -> it.getError() }
    private var _loading: LiveData<Boolean> = Transformations.switchMap(userDataSourceFactory.getDataFactoryLiveData()
    ) { it -> it.getLoading() }


    fun getError(): LiveData<Boolean> {
        return _error
    }


    fun getLoading(): LiveData<Boolean> {
        return _loading
    }


    fun fetchUsers(): LiveData<PagedList<User>> {
        val config = PagedList.Config.Builder()
                .setPageSize(PAGED_LIST_PAGE_SIZE)
                .setInitialLoadSizeHint(PAGED_LIST_PAGE_SIZE * 3)
                .build()
        return LivePagedListBuilder<Int, User>(userDataSourceFactory, config).build()
    }

    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 20
    }
}