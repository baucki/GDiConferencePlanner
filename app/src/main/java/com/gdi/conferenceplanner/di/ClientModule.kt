package com.gdi.conferenceplanner.di

import com.gdi.conferenceplanner.data.datasource.remote.repository.client.ClientRepository
import com.gdi.conferenceplanner.data.datasource.remote.repository.client.ClientRepositoryImpl
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