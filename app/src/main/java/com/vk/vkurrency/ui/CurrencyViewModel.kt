package com.vk.vkurrency.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.vkurrency.domain.GetCurrencyRateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    // Единственный ViewModel т.к. проект прост. Вынесен для удобства вне пакетов, потому что является общим и связующим для обеих фрагментов

    private var _currentCountryCodeStateFlow = MutableStateFlow<String?>(null)
    val currentCountryCodeStateFlow = _currentCountryCodeStateFlow.asStateFlow()

    private var _currentCountStateFlow = MutableStateFlow<Double?>(null)
    val currentCountStateFlow = _currentCountStateFlow.asStateFlow()

    fun setData(currencyCode: String, count: Double) {
        _currentCountryCodeStateFlow.value = currencyCode
        _currentCountStateFlow.value = count
    }

    fun getCurrencyRate(): Map<String, Float> {
        var currentRates = mutableMapOf("USD" to 0f, "EUR" to 0f, "GBP" to 0f)
        if (currentCountryCodeStateFlow.value != null && currentCountStateFlow.value != null) {
            viewModelScope.launch {
                runCatching {
                    GetCurrencyRateUseCase().execute(currentCountryCodeStateFlow.value!!)
                }.fold(
                    onSuccess = {
                        Log.d("RESPONSE SUCCESS", "$it")
                        // решил не заморачиваться с циклом для присвоения значений в массив - если валют было бы больше, то использовал бы + так меньше шансов на ошибку при присвоении
                        currentRates["USD"] = it.conversion_rates!!.USD
                        currentRates["EUR"] = it.conversion_rates.EUR
                        currentRates["GBP"] = it.conversion_rates.GBP
                    },
                    onFailure = {
                        Log.d("RESPONSE FAILURE", "$it")
                        currentRates = mutableMapOf("USD" to 0f, "EUR" to 0f, "GBP" to 0f)
                    }
                )
            }
            return currentRates.toMap()
        } else {
            currentRates.forEach { item -> currentRates.remove(item.key) }
            return currentRates.toMap()
        }
    }

}