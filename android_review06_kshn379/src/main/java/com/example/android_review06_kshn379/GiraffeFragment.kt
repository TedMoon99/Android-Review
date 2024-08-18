package com.example.android_review06_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentGiraffeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GiraffeFragment : Fragment() {

    private lateinit var binding: FragmentGiraffeBinding
    private val viewModel: GiraffeViewModel by activityViewModels()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giraffe, container, false)
        binding.giraffeViewModel = viewModel
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
            viewModel.giraffeName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..8) {
                        editTextGiraffeName.error = null
                    } else {
                        editTextGiraffeName.error = "기린 이름은 8자 이하로 입력해 주세요"
                    }
                } else {
                    editTextGiraffeName.error = "이름을 입력 하세요"
                }
            }

            // age
            viewModel.giraffeAge.observe(viewLifecycleOwner) { age ->
                if (age != null && age.isNotEmpty()) {
                    if (age.toInt() in 1..100) {
                        editTextGiraffeAge.error = null
                    } else {
                        editTextGiraffeAge.error = "기린 나이는 100살 이하로 입력해 주세요"
                    }
                } else {
                    editTextGiraffeAge.error = "나이를 입력 하세요"
                }
            }

            // strip
            viewModel.giraffeNeck.observe(viewLifecycleOwner) { neck ->
                if (neck != null && neck.isNotEmpty()) {
                    if (neck.toInt() in 1..100) {
                        editTextGiraffeNeck.error = null
                    } else {
                        editTextGiraffeNeck.error = "기린 목의 길이는 100cm 이하로 입력해 주세요"
                    }
                } else {
                    editTextGiraffeNeck.error = "목의 길이를 입력 하세요"
                }
            }

            // weight
            viewModel.giraffeRun.observe(viewLifecycleOwner) { run ->
                if (run != null && run.isNotEmpty()) {
                    if (run.toInt() in 1..100) {
                        editTextGiraffeRun.error = null
                    } else {
                        editTextGiraffeRun.error = "기린 달리기 속도는 100km/h 이하로 입력해 주세요"
                    }
                } else {
                    editTextGiraffeRun.error = "달리기 속도를 입력 하세요"
                }
            }
        }
    }
}