package com.abelsuviri.livecurrencyrate.di.module

import com.abelsuviri.data.network.CurrencyRateService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Abel Suviri
 */

@Module
class ServiceModule {

    private val baseUrl = "https://revolut.duckdns.org/"

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun transportService(retrofit: Retrofit): CurrencyRateService {
        return retrofit.create(CurrencyRateService::class.java)
    }
}