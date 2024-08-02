package com.example.android_review06_baek08102.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.databinding.FragmentLionBinding
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.model.LionData
import com.example.android_review06_baek08102.viewmodel.LionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LionFragment : Fragment(), InputFragment.DataInputListener {
    private lateinit var binding: FragmentLionBinding
    private val viewModel: LionViewModel by activityViewModels()
    private var isInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lion, container, false)
        binding.lionViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        settingError()
        settingDropdown()
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

    // 입력값 유효성검사
    fun validateInput(): Boolean {
        val name = viewModel.lionName.value ?: ""
        val age = viewModel.lionAge.value ?: ""
        val hairCount = viewModel.hairCount.value ?: ""
        val sex = viewModel.sex.value ?: ""

        Log.d("validateInput", "name : $name, age : $age, hairCount : $hairCount, sex : $sex")

        if (name.isEmpty() || name.length < 1 || name.length > 10) {
            return false
        }
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }
        if (hairCount.isEmpty() || hairCount.toIntOrNull() == null || hairCount.toInt() < 0 || hairCount.toInt() > 1000000) {
            return false
        }
        if (sex.isEmpty()) {
            return false
        }
        return true
    }

    fun settingError() {
        binding.apply {

            lionInputEdittextName.doAfterTextChanged {
                viewModel.lionName.observe(viewLifecycleOwner) { name ->
                    if (name.isNotEmpty() && name != null) {
                        if (name.length in 1..10) {
                            lionTextInputLayoutName.error = null
                        } else {
                            lionTextInputLayoutName.error = "이름은 최소 한 자에서 최대 열 자여야 합니다"
                        }
                    } else {
                        lionTextInputLayoutName.error = "이름을 입력해주세요"
                    }
                }
            }

            lionInputEdittextAge.doAfterTextChanged {
                viewModel.lionAge.observe(viewLifecycleOwner) { age ->
                    if (age.isNotEmpty() && age != null) {
                        if (age.toInt() in 1..100) {
                            lionTextInputLayoutAge.error = null
                        } else {
                            lionTextInputLayoutAge.error = "나이는 최소 한 살에서 최대 백 살이어야 합니다"
                        }
                    } else {
                        lionTextInputLayoutAge.error = "나이를 입력해주세요"
                    }
                }
            }

            lionInputEdittextHairCount.doAfterTextChanged {
                viewModel.hairCount.observe(viewLifecycleOwner) { hairCount ->
                    if (hairCount.isNotEmpty() && hairCount != null) {
                        if (hairCount.toInt() in 0..1000000) {
                            lionTextInputLayoutHairCount.error = null
                        } else {
                            lionTextInputLayoutHairCount.error = "털의 개수는 0 가닥에서 1,000,000 가닥 사이여야 합니다"
                        }
                    } else {
                        lionTextInputLayoutHairCount.error = "털의 개수를 입력해주세요"
                    }
                }
            }

            lionAutoCompleteTextViewSex.doAfterTextChanged {
                viewModel.sex.observe(viewLifecycleOwner) { sex ->
                    if (sex.isNotEmpty() && sex != null) {
                        lionTextInputLayoutSex.error = null
                    } else {
                        lionTextInputLayoutSex.error = "성별을 입력해주세요"
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

                val lionName = viewModel.lionName.value ?: ""
                val lionAge = viewModel.lionAge.value?.toInt() ?: 0
                val hairCount = viewModel.hairCount.value ?: ""
                val sex = viewModel.sex.value ?: ""

                val inputData = AnimalData(0, lionName, lionAge, hairCount, sex, index, true)

                withContext(Dispatchers.IO) { AnimalDao.saveAnimalData(inputData) }

            } catch (e: Exception) {
                Log.e("LionFragment", "save Lion Input failed : ${e.message}")
            }
        }

    }

    fun emptyInput() {
        if (!isInitialized) {
            viewModel.apply {
                lionName.value = ""
                lionAge.value = ""
                hairCount.value = ""
                sex.value = ""
            }
            isInitialized = true
        }
    }

    // 드롭다운 설정
    fun settingDropdown() {
        binding.apply {
            val itemArray = resources.getStringArray(R.array.select_sex)
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, itemArray)
            lionAutoCompleteTextViewSex.setAdapter(adapter)
        }
    }

    //
    override fun onSaveRequest() {
        saveInput()
    }

    override fun isDataValidate(): Boolean {
        return validateInput()
    }
}