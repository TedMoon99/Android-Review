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
import com.example.android_review06_baek08102.databinding.FragmentGiraffeBinding
import com.example.android_review06_baek08102.databinding.FragmentLionBinding
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.viewmodel.GiraffeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GiraffeFragment : Fragment(), InputFragment.DataInputListener {
    private lateinit var binding: FragmentGiraffeBinding
    private val viewModel: GiraffeViewModel by activityViewModels()
    private var isInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giraffe, container, false)
        binding.giraffeViewModel = viewModel
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
        val name = viewModel.giraffeName.value ?: ""
        val age = viewModel.giraffeAge.value ?: ""
        val neckLength = viewModel.neckLength.value ?: ""
        val runningSpeed = viewModel.runningSpeed.value ?: ""

        Log.d(
            "validateInput",
            "name : $name, age : $age, neckLength : $neckLength, runningSpeed : $runningSpeed"
        )

        if (name.isEmpty() || name.length < 1 || name.length > 10) {
            return false
        }
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }
        if (neckLength.isEmpty() || neckLength.toDoubleOrNull() == null || neckLength.toDouble() < 1.0 || neckLength.toDouble() > 10.0) {
            return false
        }
        if (runningSpeed.isEmpty() || runningSpeed.toDoubleOrNull() == null || runningSpeed.toDouble() < 1.0 || runningSpeed.toDouble() > 40.0) {
            return false
        }
        return true
    }

    fun settingError() {
        binding.apply {

            giraffeInputEdittextName.doAfterTextChanged {
                viewModel.giraffeName.observe(viewLifecycleOwner) { name ->
                    if (name.isNotEmpty() && name != null) {
                        if (name.length in 1..10) {
                            giraffeTextInputLayoutName.error = null
                        } else {
                            giraffeTextInputLayoutName.error = "이름은 최소 한 자에서 최대 열 자여야 합니다"
                        }
                    } else {
                        giraffeTextInputLayoutName.error = "이름을 입력해주세요"
                    }
                }
            }

            giraffeInputEdittextAge.doAfterTextChanged {
                viewModel.giraffeAge.observe(viewLifecycleOwner) { age ->
                    if (age.isNotEmpty() && age != null) {
                        if (age.toInt() in 1..100) {
                            giraffeTextInputLayoutAge.error = null
                        } else {
                            giraffeTextInputLayoutAge.error = "나이는 최소 한 살에서 최대 백 살이어야 합니다"
                        }
                    } else {
                        giraffeTextInputLayoutAge.error = "나이를 입력해주세요"
                    }
                }
            }

            giraffeInputEdittextNeckLength.doAfterTextChanged {
                viewModel.neckLength.observe(viewLifecycleOwner) { hairCount ->
                    if (hairCount.isNotEmpty() && hairCount != null) {
                        if (hairCount.toDouble() in 1.0..10.0) {
                            giraffeTextInputLayoutNeckLength.error = null
                        } else {
                            giraffeTextInputLayoutNeckLength.error = "목의 길이는 1 미터에서 10 미터 사이여야 합니다"
                        }
                    } else {
                        giraffeTextInputLayoutNeckLength.error = "목의 길이를 입력해주세요"
                    }
                }
            }

            giraffeInputEdittextRunningSpeed.doAfterTextChanged {
                viewModel.runningSpeed.observe(viewLifecycleOwner) { runningSpeed ->
                    if (runningSpeed.isNotEmpty() && runningSpeed != null) {
                        if (runningSpeed.toDouble() in 1.0..40.0) {
                            giraffeTextInputLayoutRunningSpeed.error = null
                        } else {
                            giraffeTextInputLayoutRunningSpeed.error = "달리는 속도는 시속 1 km에서 40 km 사이여야 합니다"
                        }
                    } else {
                        giraffeTextInputLayoutRunningSpeed.error = "달리는 속도를 입력해주세요"
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

                val giraffeName = viewModel.giraffeName.value ?: ""
                val giraffeAge = viewModel.giraffeAge.value?.toInt() ?: 0
                val neckLength = viewModel.neckLength.value ?: ""
                val runningSpeed = viewModel.runningSpeed.value ?: ""

                val inputData = AnimalData(2, giraffeName, giraffeAge, neckLength, runningSpeed, index, true)

                withContext(Dispatchers.IO) { AnimalDao.saveAnimalData(inputData) }

            } catch (e: Exception) {
                Log.e("GiraffeFragment", "save Giraffe Input failed : ${e.message}")
            }
        }
    }

    fun emptyInput() {
        if(!isInitialized) {
            viewModel.apply {
                giraffeName.value = ""
                giraffeAge.value = ""
                neckLength.value = ""
                runningSpeed.value = ""
            }
            isInitialized=true
        }
    }

    override fun onSaveRequest() {
        saveInput()
    }

    override fun isDataValidate(): Boolean {
        return validateInput()
    }
}