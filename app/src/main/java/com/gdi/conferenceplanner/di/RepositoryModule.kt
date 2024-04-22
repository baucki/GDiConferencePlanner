package com.gdi.conferenceplanner.di

import com.gdi.conferenceplanner.data.datasource.remote.repository.course.RemoteRepositoryImpl
import com.gdi.conferenceplanner.data.datasource.remote.repository.course.RemoteRepository
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