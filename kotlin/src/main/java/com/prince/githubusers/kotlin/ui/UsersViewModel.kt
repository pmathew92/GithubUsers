package com.prince.githubusers.kotlin.ui

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.prince.githubusers.kotlin.data.repository.UserRepository
import com.prince.githubusers.kotlin.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class UsersViewModel(private val userRepository: UserRepository,
                     userDataSourceFactory: UserDataSourceFactory) : ViewModel() {

    private var userId = 0

    private var userList: MutableLiveData<List<User>> = MutableLiveData()
    private var userLists: LiveData<PagedList<User>>
    private var loading: ObservableBoolean = ObservableBoolean(false)
    private var error: ObservableBoolean = ObservableBoolean(false)

    private var loadingFirstTime = true


    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(PAGED_LIST_ENABLE_PLACEHOLDERS)
                .setPageSize(PAGED_LIST_PAGE_SIZE)
                .setInitialLoadSizeHint(PAGED_LIST_PAGE_SIZE * 2)
                .build()

        userLists = LivePagedListBuilder<Int, User>(userDataSourceFactory, config).build()
    }

    /**
     * Method observing user list
     */
    fun getUsers(): LiveData<List<User>> {
        return userList
    }

    fun getUserLists(): LiveData<PagedList<User>> {
        return userLists
    }

    fun isLoading(): ObservableBoolean {
        return loading
    }

    fun isError(): ObservableBoolean {
        return error
    }


    /**
     * Method to reload fetching the user data
     */
    fun reload() {
        error.set(false)
        loadingFirstTime = true
        fetchUsers()
    }

    /**
     * Method to fetch users from repository
     */
    fun fetchUsers() {
        userRepository.getUsers(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (loadingFirstTime) loading.set(true)
                }
                .doAfterTerminate {
                    loading.set(false)
                    loadingFirstTime = false
                }
                .subscribe({ users ->
                    userList.value = users
                }, { err ->
                    error.set(true)
                    Timber.e(err, "Error fetching data")
                })
    }


    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 20
        private const val PAGED_LIST_ENABLE_PLACEHOLDERS = false
    }
}