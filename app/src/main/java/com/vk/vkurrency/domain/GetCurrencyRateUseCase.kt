package com.vk.vkurrency.domain

import com.vk.vkurrency.data.Retrofit
import com.vk.vkurrency.data.models.CurrencyModel

class GetCurrencyRateUseCase {
    suspend fun execute(currencyCode: String): CurrencyModel {
        return Retrofit().retrofit.getCurrency(currencyCode)
    }
}