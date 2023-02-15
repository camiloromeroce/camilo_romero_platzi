package com.example.camilo_romero_platzi.presentation.di

import com.example.camilo_romero_platzi.presentation.data.network.MarvelApiClient
import com.example.camilo_romero_platzi.presentation.ui.common.getCurrentMD5
import com.example.domain.home.common.API_KEY
import com.example.domain.home.common.API_LIMIT
import com.example.domain.home.common.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMarvelApiClient(retrofit: Retrofit): MarvelApiClient =
        retrofit.create(MarvelApiClient::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    object HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val time = Calendar.getInstance().time.time.toString()
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", time)
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("hash", getCurrentMD5(time))
                .addQueryParameter("limit", API_LIMIT)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}