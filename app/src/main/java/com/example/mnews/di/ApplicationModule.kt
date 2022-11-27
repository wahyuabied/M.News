package com.example.mnews.di

import android.util.Log
import com.example.mnews.BuildConfig
import com.example.mnews.home.view_model.MainViewModel
import com.example.mnews.network.MNewsAPI
import com.example.mnews.network.MNewsRepository
import com.example.mnews.network.MNewsRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/"

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    private val TAG = "NewsApp"

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, message)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideMNewsAPI(retrofit: Retrofit): MNewsAPI = retrofit.create(MNewsAPI::class.java)

    @Provides
    @Singleton
    fun provideMNewsRepository(mNewsApi: MNewsAPI): MNewsRepositoryImpl = MNewsRepositoryImpl(mNewsApi)

    @Provides
    @Singleton
    fun provideMainViewModel(repo: MNewsRepositoryImpl): MainViewModel = MainViewModel(repo)
}
