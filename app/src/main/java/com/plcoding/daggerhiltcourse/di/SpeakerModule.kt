package com.plcoding.daggerhiltcourse.di

import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.SpeakerRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.SpeakerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpeakerModule {
    @Binds
    @Singleton
    abstract fun bindSpeakerRepository(
        mySpeakerRepository: SpeakerRepositoryImpl
    ): SpeakerRepository
}