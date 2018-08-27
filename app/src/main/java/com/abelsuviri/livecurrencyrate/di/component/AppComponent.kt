package com.abelsuviri.livecurrencyrate.di.component

import com.abelsuviri.livecurrencyrate.CurrencyRateApp
import com.abelsuviri.livecurrencyrate.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * @author Abel Suviri
 */

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class])
interface AppComponent {
    fun inject(app: CurrencyRateApp)
}