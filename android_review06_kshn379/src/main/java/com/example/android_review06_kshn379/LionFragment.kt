package com.example.android_review06_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentLionBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LionFragment : Fragment() {
    private lateinit var binding: FragmentLionBinding
    private val viewModel: LionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lion, container, false)
        binding.lionViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()

        // Event 설정
        settingEvent()

        // Error 설정
        settingError()

    }

    // View 설정
    private fun settingView() {
        binding.apply {
            viewModel.initInput()
            // 스위치 변경 업데이트
            switchLionGender.isChecked = viewModel.lionGender.value == "암컷"
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // 스위치 설정
            switchLionGender.setOnCheckedChangeListener { _, isChecked ->
                viewModel.switchGender(isChecked)
            }
        }
    }

    // Error 설정
    private fun settingError() {
        binding.apply {
            // name
            viewModel.lionName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..8) {
                        editTextLionName.error = null
                    } else {
                        editTextLionName.error = "사자 이름은 8자 이하로 입력해 주세요"
                    }
                } else {
                    editTextLionName.error = "이름을 입력 하세요"
                }
            }

            // age
            viewModel.lionAge.observe(viewLifecycleOwner) { age ->
                if (age != null && age.isNotEmpty()) {
                    if (age.toInt() in 1..100) {
                        editTextLionAge.error = null
                    } else {
                        editTextLionAge.error = "사자 나이는 100살 이하로 입력해 주세요"
                    }
                } else {
                    editTextLionAge.error = "나이를 입력 하세요"
                }
            }

            // fur
            viewModel.lionFur.observe(viewLifecycleOwner) { fur ->
                if (fur != null && fur.isNotEmpty()) {
                    if (fur.toInt() in 1..100) {
                        editTextLionFur.error = null
                    } else {
                        editTextLionFur.error = "사자 털의 갯수는 100개 이하로 입력해 주세요"
                    }
                } else {
                    editTextLionFur.error = "털의 갯수를 입력 하세요"
                }
            }
        }
    }
}