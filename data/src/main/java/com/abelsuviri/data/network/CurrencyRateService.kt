package com.abelsuviri.data.network

import com.abelsuviri.data.model.RatesModel
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Abel Suviri
 */

interface CurrencyRateService {
    @GET("latest") fun getRate(
            @Query("base") baseCurrency: String
    ): Deferred<RatesModel>
}