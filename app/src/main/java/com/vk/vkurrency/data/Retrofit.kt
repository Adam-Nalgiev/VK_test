package com.vk.vkurrency.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    val retrofit: API = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(API::class.java)

    companion object {
        private const val token = "1a8534937c8ae4da4332e0a8"
        private const val baseURL = "https://v6.exchangerate-api.com/v6/$token/latest/"
    }
}