package com.abelsuviri.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Abel Suviri
 */

data class RatesModel(
        @SerializedName("base") val baseCurrency: String,
        @SerializedName("rates") val rates: LinkedHashMap<String, Double>
)