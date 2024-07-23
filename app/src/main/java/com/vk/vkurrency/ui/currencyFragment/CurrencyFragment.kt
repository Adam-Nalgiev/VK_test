package com.vk.vkurrency.ui.currencyFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vk.vkurrency.R
import com.vk.vkurrency.databinding.FragmentCurrencyBinding
import com.vk.vkurrency.ui.CurrencyViewModel
import com.vk.vkurrency.ui.MainActivity

class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!
    private val currencyViewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()

        binding.countValue.doOnTextChanged { text, _, _, _ ->
            binding.warning.isVisible = (text == "" || text == "0")
        }
        binding.convertButton.setOnClickListener {
            if (binding.countValue.text.toString() == "" || binding.countValue.text.toString() == "0") {
                binding.warning.visibility = View.VISIBLE
            } else {
                val selectedItemCode = when (binding.currencyCodes.selectedItemId.toInt()) {
                    1 -> "EUR"
                    2 -> "GBP"
                    else -> "USD"
                }

                Log.d("VALUE", "${binding.countValue.text.toString().toFloat()}")
                val bundle = bundleOf(MainActivity.CURRENCY_KEY to selectedItemCode, MainActivity.COUNT_KEY to binding.countValue.text.toString().toFloat())

                findNavController().navigate(R.id.action_currencyFragment_to_resultFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createAdapter() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_codes,
            R.layout.spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
            binding.currencyCodes.adapter = arrayAdapter
        }
    }
}