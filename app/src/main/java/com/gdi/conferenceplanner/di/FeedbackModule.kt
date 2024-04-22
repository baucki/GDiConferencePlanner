package com.gdi.conferenceplanner.di

import com.gdi.conferenceplanner.data.datasource.remote.repository.feedback.FeedbackRepository
import com.gdi.conferenceplanner.data.datasource.remote.repository.feedback.FeedbackRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedbackModule {
    @Binds
    @Singleton
    abstract fun bindMyFeedbackRepository(
        myFeedbackRepository: FeedbackRepositoryImpl
    ): FeedbackRepository
}