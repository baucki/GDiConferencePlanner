package com.plcoding.daggerhiltcourse.di

import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.course.RemoteRepositoryImpl
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.course.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMyRepository(
        myRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository
}