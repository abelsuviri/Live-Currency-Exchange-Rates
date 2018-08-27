package com.abelsuviri.data.repository

import com.abelsuviri.data.model.RatesModel
import com.abelsuviri.data.network.CurrencyRateService
import com.abelsuviri.data.utils.Result
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Abel Suviri
 */

class LiveRateRepository @Inject constructor(private val currencyRateService: CurrencyRateService) {
    suspend fun getCurrencyRates(baseCurrency: String): Result<RatesModel> {
        return try {
            val response = currencyRateService.getRate(baseCurrency)
            val result = response.await()

            Result.Success(result)
        } catch (e: HttpException) {
            Result.Error(e)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }
}