package com.gita.mobilebankinguicompose.di

import com.gita.mobilebankinguicompose.navigation.AppNavigator
import com.gita.mobilebankinguicompose.navigation.MyNavigationManager
import com.gita.mobilebankinguicompose.navigation.NavigationHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindNavigationHandler(impl: MyNavigationManager): NavigationHandler

    @Binds
    fun bindAppNavigator(impl: MyNavigationManager): AppNavigator
}