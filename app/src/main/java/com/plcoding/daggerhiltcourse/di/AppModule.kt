package com.plcoding.daggerhiltcourse.di

import android.app.Application
import androidx.room.Room
import com.plcoding.daggerhiltcourse.data.datasource.local.CourseDatabase
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepository
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepositoryImpl
import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyApi(): MyApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.5.53:8080/course/")
//            .baseUrl("http://192.168.0.26:8080/course/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCourseDatabase(app: Application): CourseDatabase {
        return Room.databaseBuilder(
            app,
            CourseDatabase::class.java,
            "courses_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(db: CourseDatabase): LocalRepository {
        return LocalRepositoryImpl(db.courseDao)
    }

}