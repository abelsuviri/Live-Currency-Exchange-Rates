package com.abelsuviri.livecurrencyrate.di.module

import com.abelsuviri.livecurrencyrate.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Abel Suviri
 */

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}