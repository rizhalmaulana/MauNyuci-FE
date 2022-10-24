package com.stp.maunyucibeta.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.stp.maunyucibeta.BuildConfig
import com.stp.maunyucibeta.data.MauNyuciDatabase
import com.stp.maunyucibeta.data.UserDao
import com.stp.maunyucibeta.data.UserRepository
import com.stp.maunyucibeta.repository.RemoteRepositoryDao
import com.stp.maunyucibeta.repository.RemoteRepositoryDaoImpl
import com.stp.maunyucibeta.repository.RemoteRepositoryService
import com.stp.maunyucibeta.repository.ResourceRepository
import com.stp.maunyucibeta.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun cache(cacheFile: File): Cache = Cache(cacheFile, 10 * 1000 * 1000) //10MB Cahe

    @Provides
    fun cacheFile(@ApplicationContext context: Context): File =
        File(context.cacheDir, "okhttp_cache")

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache?,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            connectTimeout(180, TimeUnit.SECONDS)
            readTimeout(180, TimeUnit.SECONDS)
            writeTimeout(180, TimeUnit.SECONDS)
            addNetworkInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideRemoteRepositoryService(retrofit: Retrofit): RemoteRepositoryService =
        retrofit.create(RemoteRepositoryService::class.java)

    @Provides
    fun provideRemoteRepositoryImpl(remoteRepositoryImpl: RemoteRepositoryDaoImpl): RemoteRepositoryDao =
        remoteRepositoryImpl

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MauNyuciDatabase =
        Room.databaseBuilder(
            context,
            MauNyuciDatabase::class.java,
            "maunyucibeta_data_database"
        ).fallbackToDestructiveMigration().build()


    @Provides
    fun providesPostDao(mauNyuciDatabase: MauNyuciDatabase): UserDao =
        mauNyuciDatabase.getUserDao()

    @Provides
    fun providesPostRepository(userDao: UserDao): UserRepository =
        UserRepository(userDao)

    @Provides
    fun provideResourceRepository(
        application: Application
    ): ResourceRepository = ResourceRepository(application.baseContext)

    @Provides
    fun providePreferenceManager(application: Application): PreferenceManager =
        PreferenceManager(application.baseContext)
}