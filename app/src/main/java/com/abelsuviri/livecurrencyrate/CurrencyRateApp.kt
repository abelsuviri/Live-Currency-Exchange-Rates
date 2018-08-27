package com.abelsuviri.livecurrencyrate

import android.app.Activity
import android.app.Application
import com.abelsuviri.livecurrencyrate.di.component.AppComponent
import com.abelsuviri.livecurrencyrate.di.component.DaggerAppComponent
import com.abelsuviri.livecurrencyrate.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * @author Abel Suviri
 */

class CurrencyRateApp: Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

   val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }
}