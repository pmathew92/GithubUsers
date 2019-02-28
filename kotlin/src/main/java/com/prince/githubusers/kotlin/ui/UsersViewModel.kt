package com.prince.githubusers.kotlin.ui

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prince.githubusers.kotlin.data.repository.UserRepository
import com.prince.githubusers.kotlin.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class UsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var userId = 0

    private var userList: MutableLiveData<List<User>> = MutableLiveData()
    private var loading: ObservableBoolean = ObservableBoolean(false)
    private var error: ObservableBoolean = ObservableBoolean(false)

    private var loadingFirstTime = true

    init {
        fetchUsers()
    }

    /**
     * Method observing user list
     */
    fun getUsers(): LiveData<List<User>> {
        return userList
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
        getUsers()
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
}