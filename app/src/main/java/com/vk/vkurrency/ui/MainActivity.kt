package com.vk.vkurrency.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.vkurrency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
    companion object{
        const val CURRENCY_KEY = "currency"
        const val COUNT_KEY = "count"
    }
}
