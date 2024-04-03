package com.plcoding.daggerhiltcourse.di

import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  UserModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        myUserRepository: UserRepositoryImpl
    ): UserRepository
}