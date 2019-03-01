package com.prince.githubusers.kotlin.ui

import dagger.Module
import dagger.Provides

@Module
class UserActivityModule {

    @Provides
    fun provideUserAdapter(context: UsersActivity): UserAdapter {
        return UserAdapter(context)
    }
}