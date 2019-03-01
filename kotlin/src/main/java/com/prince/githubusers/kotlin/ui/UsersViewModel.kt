package com.prince.githubusers.kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.prince.githubusers.kotlin.data.repository.UserRepository
import com.prince.githubusers.kotlin.model.User

class UsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val fetchData = MutableLiveData<Void>()


    private val userLists: LiveData<PagedList<User>> = Transformations.switchMap(fetchData) { it -> userRepository.fetchUsers() }


    private var loading: LiveData<Boolean> = Transformations.switchMap(userLists) {
        userRepository.getLoading()
    }

    private var error: LiveData<Boolean> = Transformations.switchMap(userLists) {
        userRepository.getError()
    }

    init {
        fetchData.value = null
    }


    fun getUserLists(): LiveData<PagedList<User>> {
        return userLists
    }

    fun isLoading(): LiveData<Boolean> {
        return loading
    }

    fun isError(): LiveData<Boolean> {
        return error
    }


    /**
     * Method to reload the initial fetching of user data
     */
    fun reload() {
        fetchData.value = null
    }

}