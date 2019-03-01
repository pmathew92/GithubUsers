package com.prince.githubusers.kotlin.di

import com.prince.githubusers.kotlin.ui.UserActivityModule
import com.prince.githubusers.kotlin.ui.UsersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [UserActivityModule::class])
    abstract fun bindUserActivity(): UsersActivity
}