package com.abelsuviri.livecurrencyrate.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Abel Suviri
 */

@Module(includes = [ViewModelModule::class, ActivityBuilderModule::class, ServiceModule::class])
class AppModule(val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context = context
}