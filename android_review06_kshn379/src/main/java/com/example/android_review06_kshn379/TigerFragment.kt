package com.example.android_review06_kshn379

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentTigerBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TigerFragment : Fragment() {
    private lateinit var binding: FragmentTigerBinding
    private val viewModel: TigerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tiger, container, false)
        binding.tigerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()

        // Error 설정
        settingError()

    }

    // View 설정
    private fun settingView() {
        binding.apply {
            viewModel.initInput()
        }
    }

    // Error 설정
    private fun settingError() {
        binding.apply {
            // name
            viewModel.tigerName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..8) {
                        editTextTigerName.error = null
                    } else {
                        editTextTigerName.error = "호랑이 이름은 8자 이하로 입력해 주세요"
                    }
                } else {
                    editTextTigerName.error = "이름을 입력 하세요"
                }
            }

            // age
            viewModel.tigerAge.observe(viewLifecycleOwner) { age ->
                if (age != null && age.isNotEmpty()) {
                    if (age.toInt() in 1..100) {
                        editTextTigerAge.error = null
                    } else {
                        editTextTigerAge.error = "호랑이 나이는 100살 이하로 입력해 주세요"
                    }
                } else {
                    editTextTigerAge.error = "나이를 입력 하세요"
                }
            }

            // strip
            viewModel.tigerStrip.observe(viewLifecycleOwner) { strip ->
                if (strip != null && strip.isNotEmpty()) {
                    if (strip.toInt() in 1..100) {
                        editTextTigerStrip.error = null
                    } else {
                        editTextTigerStrip.error = "호랑이 줄무늬 갯수는 100개 이하로 입력해 주세요"
                    }
                } else {
                    editTextTigerStrip.error = "줄무늬 갯수를 입력 하세요"
                }
            }

            // weight
            viewModel.tigerWeight.observe(viewLifecycleOwner) { weight ->
                if (weight != null && weight.isNotEmpty()) {
                    if (weight.toInt() in 1..100) {
                        editTextTigerWeight.error = null
                    } else {
                        editTextTigerWeight.error = "호랑이 몸무게는 100kg 이하로 입력해 주세요"
                    }
                } else {
                    editTextTigerWeight.error = "몸무게를 입력 하세요"
                }
            }
        }
    }
}