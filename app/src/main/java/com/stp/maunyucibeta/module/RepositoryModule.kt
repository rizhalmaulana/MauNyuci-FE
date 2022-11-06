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
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection
import java.util.*
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
        token: PreferenceManager
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(AuthorizationInterceptor(token.getToken()))
            if (BuildConfig.DEBUG || BuildConfig.FLAVOR == "staging") {
                addInterceptor(loggingInterceptor)
            }
            cache(cache)
            connectTimeout(180, TimeUnit.SECONDS)
            writeTimeout(180, TimeUnit.SECONDS)
            readTimeout(180, TimeUnit.SECONDS)
        }.build()
    }

    private class AuthorizationInterceptor(val token: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var response: Response
            val newToken = when {
                token.isNotEmpty() -> token
                else -> token
            }
            response = createRequest(chain, newToken ?: "")
            try {
                if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED || response.code == HttpURLConnection.HTTP_FORBIDDEN) {
                    response = token.let { createRequest(chain, it) }
                }
            } catch (exception: Exception) {
                Timber.tag(this::class.java.name).e(exception)
                response.close()
            }
            return response
        }

        private fun createRequest(chain: Interceptor.Chain, newToken: String): Response {
            val newRequest: Request = chain.request().newBuilder()
                .header("Authorization", "Bearer $newToken")
                .header("Accept-Language", Locale.getDefault().language)
                .build()
            return chain.proceed(newRequest)
        }
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