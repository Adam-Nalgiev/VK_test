package com.vk.vkurrency.ui.currencyFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vk.vkurrency.data.Retrofit

class CurrencyViewModel : ViewModel() {

    suspend fun getCurrencyRate(currencyCode: String, count: Double): Double? {
        runCatching { Retrofit().retrofit.getCurrency(currencyCode) }.fold(
            onSuccess = {
                Log.d("SUCCESS", "RESPONSE IS SUCCESS")
                Log.d("SUCCESS", "VALUE: ${it.conversion_rates.toString()}")
                if (it.conversion_rates != null){
                return it.conversion_rates.USD * count} else { return null}
                        },
            onFailure = {
                Log.d("FATAL ERROR", "ERROR OF GET CURRENCY RESPONSE")
                return null
            }
        )
    }

}