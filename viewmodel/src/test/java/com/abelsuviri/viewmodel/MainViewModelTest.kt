package com.abelsuviri.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abelsuviri.data.model.RatesModel
import com.abelsuviri.data.repository.LiveRateRepository
import com.abelsuviri.data.utils.Result
import com.abelsuviri.viewmodel.mock.MockJson
import com.abelsuviri.viewmodel.rule.RxSchedulerRule
import com.google.gson.Gson
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

/**
 * @author Abel Suviri
 */

class MainViewModelTest {
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var testSchedulerRule: TestScheduler

    @Mock
    lateinit var liveRateRepository: LiveRateRepository

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(liveRateRepository)
    }

    @Test
    fun test_get_currency_exchange_successfully() {
        val baseCurrency = "EUR"
        val gson = Gson()
        val ratesResponse = Result.Success(gson.fromJson<RatesModel>(MockJson.json, RatesModel::class.java))
        Mockito.`when`(runBlocking { liveRateRepository.getCurrencyRates(baseCurrency) }).thenReturn(ratesResponse)
        mainViewModel.getCurrencyRate(baseCurrency, "1", true)

        testSchedulerRule.advanceTimeBy(5, TimeUnit.SECONDS)
        Assert.assertEquals(mainViewModel.currencyAmount.value, ratesResponse.data.rates)
    }
}