package com.example.android_review06_baek08102.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.databinding.FragmentLionBinding
import com.example.android_review06_baek08102.databinding.FragmentTigerBinding
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.viewmodel.GiraffeViewModel
import com.example.android_review06_baek08102.viewmodel.TigerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TigerFragment : Fragment(), InputFragment.DataInputListener {
    private lateinit var binding: FragmentTigerBinding
    private val viewModel: TigerViewModel by activityViewModels()
    private var isInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tiger, container, false)
        binding.tigerViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        settingError()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
    }

    fun settingView() {
        binding.apply {
            emptyInput()
        }
    }

    fun validateInput(): Boolean {
        val name = viewModel.tigerName.value ?: ""
        val age = viewModel.tigerAge.value ?: ""
        val stripeCount = viewModel.stripeCount.value ?: ""
        val weight = viewModel.weight.value ?: ""

        Log.d(
            "validateInput",
            "name : $name, age : $age, stripeCount : $stripeCount, weight : $weight"
        )

        if (name.isEmpty() || name.length < 1 || name.length > 10) {
            return false
        }
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }
        if (stripeCount.isEmpty() || stripeCount.toIntOrNull() == null || stripeCount.toInt() < 0 || stripeCount.toInt() > 100) {
            return false
        }
        if (weight.isEmpty() || weight.toDoubleOrNull() == null || weight.toDouble() < 1.0 || weight.toDouble() > 200.0) {
            return false
        }
        return true
    }

    fun settingError() {
        binding.apply {

            tigerInputEdittextName.doAfterTextChanged {
                viewModel.tigerName.observe(viewLifecycleOwner) { name ->
                    if (name.isNotEmpty() && name != null) {
                        if (name.length in 1..10) {
                            tigerTextInputLayoutName.error = null
                        } else {
                            tigerTextInputLayoutName.error = "이름은 최소 한 자에서 최대 열 자여야 합니다"
                        }
                    } else {
                        tigerTextInputLayoutName.error = "이름을 입력해주세요"
                    }
                }
            }

            tigerInputEdittextAge.doAfterTextChanged {
                viewModel.tigerAge.observe(viewLifecycleOwner) { age ->
                    if (age.isNotEmpty() && age != null) {
                        if (age.toInt() in 1..100) {
                            tigerTextInputLayoutAge.error = null
                        } else {
                            tigerTextInputLayoutAge.error = "나이는 최소 한 살에서 최대 백 살이어야 합니다"
                        }
                    } else {
                        tigerTextInputLayoutAge.error = "나이를 입력해주세요"
                    }
                }
            }

            tigerInputEdittextStripeCount.doAfterTextChanged {
                viewModel.stripeCount.observe(viewLifecycleOwner) { hairCount ->
                    if (hairCount.isNotEmpty() && hairCount != null) {
                        if (hairCount.toInt() in 0..100) {
                            tigerTextInputLayoutStripeCount.error = null
                        } else {
                            tigerTextInputLayoutStripeCount.error = "줄무늬의 개수는 0 개에서 100 개 사이여야 합니다"
                        }
                    } else {
                        tigerTextInputLayoutStripeCount.error = "줄무늬의 개수를 입력해주세요"
                    }
                }
            }

            tigerInputEdittextWeight.doAfterTextChanged {
                viewModel.weight.observe(viewLifecycleOwner) { weight ->
                    if (weight.isNotEmpty() && weight != null) {
                        if (weight.toDouble() in 1.0..200.0) {
                            tigerTextInputLayoutWeight.error = null
                        } else {
                            tigerTextInputLayoutWeight.error = "몸무게는 1 kg에서 200 kg 사이여야 합니다"
                        }
                    } else {
                        tigerTextInputLayoutWeight.error = "몸무게를 입력해주세요"
                    }
                }
            }
        }
    }

    fun saveInput() {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val animalSequence = withContext(Dispatchers.IO) { AnimalDao.getSequence() }
                withContext(Dispatchers.IO) { AnimalDao.updateSequence(animalSequence + 1) }

                val index = animalSequence + 1

                val tigerName = viewModel.tigerName.value ?: ""
                val tigerAge = viewModel.tigerAge.value?.toInt() ?: 0
                val stripeCount = viewModel.stripeCount.value ?: ""
                val weight = viewModel.weight.value ?: ""

                val inputData = AnimalData(1, tigerName, tigerAge, stripeCount, weight, index, true)

                withContext(Dispatchers.IO) { AnimalDao.saveAnimalData(inputData) }

            } catch (e: Exception) {
                Log.e("TigerFragment", "save Tiger Input failed : ${e.message}")
            }
        }
    }

    fun emptyInput() {
        if (!isInitialized) {
            viewModel.apply {
                tigerName.value = ""
                tigerAge.value = ""
                stripeCount.value = ""
                weight.value = ""
            }
            isInitialized = true
        }
    }

    override fun onSaveRequest() {
        saveInput()
    }

    override fun isDataValidate(): Boolean {
        return validateInput()
    }
}