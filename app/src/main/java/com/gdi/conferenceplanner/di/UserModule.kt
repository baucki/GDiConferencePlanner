package com.gdi.conferenceplanner.di

import com.gdi.conferenceplanner.data.datasource.remote.repository.user.UserRepository
import com.gdi.conferenceplanner.data.datasource.remote.repository.user.UserRepositoryImpl
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