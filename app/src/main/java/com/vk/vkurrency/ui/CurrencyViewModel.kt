package com.vk.vkurrency.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.vkurrency.domain.GetCurrencyRateUseCase
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    suspend fun getCurrencyRate(baseCountryCode: String): MutableMap<String, Float> {
        var currentRates = mutableMapOf("USD" to 0f, "EUR" to 0f, "GBP" to 0f)
                runCatching {
                    GetCurrencyRateUseCase().execute(baseCountryCode)
                }.fold(
                    onSuccess = {
                        Log.d("RESPONSE SUCCESS", "$it")
                        // решил не заморачиваться с циклом для присвоения значений в массив - если валют было бы больше, то использовал бы + так меньше шансов на ошибку при присвоении
                        currentRates["USD"] = it.conversion_rates!!.USD
                        currentRates["EUR"] = it.conversion_rates.EUR
                        currentRates["GBP"] = it.conversion_rates.GBP
                        Log.d("RESPONSE SUCCESS VALUE", "$currentRates")
                    },
                    onFailure = {
                        Log.d("RESPONSE FAILURE", "$it")
                        currentRates = mutableMapOf("USD" to 0f, "EUR" to 0f, "GBP" to 0f)
                    }
                )
                return currentRates
        }
    }

