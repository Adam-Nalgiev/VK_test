package com.vk.vkurrency.data.models

data class CurrencyModel(
    val result: String,
    val documentation: String,
    val terms_of_use: String,
    val time_last_update_unix: Long,
    val time_last_update_UTC: String,
    val time_next_update_Unix: Long,
    val time_next_update_UTC: String,
    val base_code: String,
    val conversion_rates: ConversionRates,
)

