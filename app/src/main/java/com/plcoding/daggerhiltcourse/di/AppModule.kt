package com.plcoding.daggerhiltcourse.di

import android.app.Application
import androidx.room.Room
import com.plcoding.daggerhiltcourse.BuildConfig
import com.plcoding.daggerhiltcourse.data.datasource.local.ConferenceDatabase
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course_speaker.CourseSpeakerCrossRefRepository
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course_speaker.CourseSpeakerCrossRefRepositoryImpl
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course.LocalRepository
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course.LocalRepositoryImpl
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.speaker.SpeakerRepository
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.speaker.SpeakerRepositoryImpl
import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.datasource.remote.interceptor.AuthorizationInterceptor
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyApi(okHttpClient: OkHttpClient): MyApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.5.53:8080/")
//            .baseUrl("http://192.168.0.28:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        httpClient.addInterceptor(authorizationInterceptor)

        return httpClient.build()
    }
    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor {
        return AuthorizationInterceptor()
    }
    @Provides
    @Singleton
    fun provideCourseDatabase(app: Application): ConferenceDatabase {
        return Room.databaseBuilder(
            app,
            ConferenceDatabase::class.java,
            "conference_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(db: ConferenceDatabase): LocalRepository {
        return LocalRepositoryImpl(db.courseDao)
    }

    @Provides
    @Singleton
    fun provideSpeakerRepository(db: ConferenceDatabase): SpeakerRepository {
        return SpeakerRepositoryImpl(db.speakerDao)
    }

    @Provides
    @Singleton
    fun provideCourseSpeakerCrossRefRepository(db: ConferenceDatabase): CourseSpeakerCrossRefRepository {
        return CourseSpeakerCrossRefRepositoryImpl(db.crossRefDao)
    }

}