package com.prince.githubusers.kotlin.ui

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.githubusers.kotlin.data.repository.UserRepository
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(private val userRepository: UserRepository) :
        ViewModelProvider.NewInstanceFactory() {

    @NonNull
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //noinspection unchecked
        return UsersViewModel(userRepository) as T
    }
}