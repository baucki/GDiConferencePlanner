package com.gdi.conferenceplanner.di

import com.gdi.conferenceplanner.data.datasource.remote.repository.speaker.SpeakerRepository
import com.gdi.conferenceplanner.data.datasource.remote.repository.speaker.SpeakerRepositoryImpl
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