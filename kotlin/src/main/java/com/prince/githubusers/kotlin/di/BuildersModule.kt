package com.prince.githubusers.kotlin.di

import com.prince.githubusers.kotlin.ui.UsersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindUserActivity(): UsersActivity
}