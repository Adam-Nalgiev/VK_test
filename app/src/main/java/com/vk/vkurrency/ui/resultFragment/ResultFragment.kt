package com.vk.vkurrency.ui.resultFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vk.vkurrency.R
import com.vk.vkurrency.databinding.FragmentResultBinding
import com.vk.vkurrency.ui.MainActivity
import kotlinx.coroutines.launch

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val resultViewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {

            val currency = arguments?.getString(MainActivity.CURRENCY_KEY)
            val count = arguments?.getFloat(MainActivity.COUNT_KEY)

            if (currency != null && count != null) {

                val currenciesMap = resultViewModel.getCurrencyRate(currency)
                val currenciesConverted = convert(currenciesMap, count)

                if (currenciesMap.isEmpty()) {
                    binding.onCurrentTimeText.setText(R.string.error)
                }

                binding.usdRate.text = currenciesConverted["USD"].toString()
                binding.eurRate.text = currenciesConverted["EUR"].toString()
                binding.gbpRate.text = currenciesConverted["GBP"].toString()
            }else{
                binding.onCurrentTimeText.setText(R.string.error)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun convert(currencies: MutableMap<String, Float>, count: Float): Map<String, Float> {
        currencies.forEach { item ->
            currencies[item.key] = item.value * count
        }
        return currencies
    }
}