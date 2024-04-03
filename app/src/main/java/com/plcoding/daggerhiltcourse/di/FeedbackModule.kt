package com.plcoding.daggerhiltcourse.di

import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.feedback.FeedbackRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.feedback.FeedbackRepositoryImpl
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