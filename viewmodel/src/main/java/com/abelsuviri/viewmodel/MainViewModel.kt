package com.abelsuviri.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abelsuviri.data.repository.LiveRateRepository
import com.abelsuviri.data.utils.Result
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Abel Suviri
 */

class MainViewModel @Inject constructor(private val liveRateRepository: LiveRateRepository) : ViewModel() {
    val currencyAmount: MutableLiveData<LinkedHashMap<String, Double>> = MutableLiveData()
    val hasFailed: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var disposable: CompositeDisposable

    fun getCurrencyRate(baseCurrency: String, inputAmount: String, shouldExecuteOnce: Boolean) {
        hasFailed.value = false     //Set failed to false
        disposable = CompositeDisposable()

        //Check if the network call has to be executed just once or every one second
        if (shouldExecuteOnce) {
            getRate(baseCurrency, inputAmount)
        } else {
            disposable.add(Observable.interval(1, TimeUnit.SECONDS).subscribe {
                getRate(baseCurrency, inputAmount)
            })
        }

    }

    //Execute the network call
    private fun getRate(baseCurrency: String, inputAmount: String) {
        launch(CommonPool) {
            val response = liveRateRepository.getCurrencyRates(baseCurrency)
            if (response is Result.Success) {
                val currencyList: LinkedHashMap<String, Double> = LinkedHashMap()
                currencyList[response.data.baseCurrency] = if (TextUtils.isEmpty(inputAmount)) 0.0 else inputAmount.toDouble()
                currencyList.putAll(response.data.rates)
                currencyAmount.postValue(currencyList)
            } else if (response is Result.Error) {
                hasFailed.postValue(true)
            }
        }
    }

    fun onUpdatingRate() {
        disposable.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}