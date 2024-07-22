package com.vk.vkurrency.data

import com.vk.vkurrency.data.models.CurrencyModel
import retrofit2.http.GET
import retrofit2.http.Path

interface API {

    @GET("{id}")
    suspend fun getCurrency(
        @Path("id") id: String
    ): CurrencyModel
}