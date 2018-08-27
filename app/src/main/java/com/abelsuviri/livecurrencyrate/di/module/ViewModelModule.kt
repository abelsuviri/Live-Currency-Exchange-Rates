package com.abelsuviri.livecurrencyrate.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abelsuviri.livecurrencyrate.di.scope.ViewModelScope
import com.abelsuviri.viewmodel.MainViewModel
import com.abelsuviri.viewmodel.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * @author Abel Suviri
 */

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelScope(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}