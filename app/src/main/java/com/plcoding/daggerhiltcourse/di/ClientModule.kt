package com.plcoding.daggerhiltcourse.di

import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.ClientRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.ClientRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClientModule {
    @Binds
    @Singleton
    abstract fun bindMyClientRepository(
        myClientRepository: ClientRepositoryImpl
    ): ClientRepository
}