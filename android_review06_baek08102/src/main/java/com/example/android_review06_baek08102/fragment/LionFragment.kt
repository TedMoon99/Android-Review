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

    // 해당 Fragment가 이미 attach 되었는지 확인
    // InputFragment의 fragment container에 처음으로 담기는 시점에서만
    // 이전에 입력해둔 InputData 초기화 시켜주기 위해 사용되는 값
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

    // 프래그먼트가 다시 보이게 될 때마다 에러와 드롭다운을 새로 설정
    // 자세한 건 Readme에
    override fun onStart() {
        super.onStart()

        settingError()
        settingDropdown()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
    }

    // 뷰 설정
    fun settingView() {
        binding.apply {
            emptyInput()
        }
    }

    // 입력값 유효성검사
    fun validateInput(): Boolean {
        // viewModel로부터 사용자가 입력한 데이터 받아와서
        val name = viewModel.lionName.value ?: ""
        val age = viewModel.lionAge.value ?: ""
        val hairCount = viewModel.hairCount.value ?: ""
        val sex = viewModel.sex.value ?: ""

        Log.d("validateInput", "name : $name, age : $age, hairCount : $hairCount, sex : $sex")

        // 각 부분에서 검사 진행 후 통과하지 못한다면 false 리턴 후 함수 종료
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

        // 순차적으로 진행되는 모든 검사를 통과한 후에 true 리턴 후 함수 종료
        return true
    }

    // 에러 설정
    fun settingError() {
        binding.apply {

            // 사용자가 값을 입력한 후부터 에러 출력
            lionInputEdittextName.doAfterTextChanged {

                // 실시간 입력값 관측하여
                // 각 조건에 맞는 에러 출력
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
        }
    }

    // 입력값 저장
    fun saveInput() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 현재 시퀀스값 받아와서
                val animalSequence = withContext(Dispatchers.IO) { AnimalDao.getSequence() }
                // 데이터 저장 시마다 업데이트 해주고
                withContext(Dispatchers.IO) { AnimalDao.updateSequence(animalSequence + 1) }

                // 시퀀스 + 1로 인덱스값 설정
                val index = animalSequence + 1

                // 나이 제외한 모든 값 입력 시에는 String 타입으로
                val lionName = viewModel.lionName.value ?: ""
                val lionAge = viewModel.lionAge.value?.toInt() ?: 0
                val hairCount = viewModel.hairCount.value ?: ""
                val sex = viewModel.sex.value ?: ""

                // 각 동물들 모두 AnimalData 타입으로 통일하여 입력
                val inputData = AnimalData(0, lionName, lionAge, hairCount, sex, index, true)

                withContext(Dispatchers.IO) { AnimalDao.saveAnimalData(inputData) }

            } catch (e: Exception) {
                Log.e("LionFragment", "save Lion Input failed : ${e.message}")
            }
        }

    }

    // 인스턴스 재생성 시
    // 기존에 viewModel에 남아있던 입력값 초기화
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

    // InputFragment에서 onSaveRequest 호출 시 saveInput() 실행
    override fun onSaveRequest() {
        saveInput()
    }

    // InputFragment에 입력값의 유효성 검사 결과 전달
    override fun isDataValidate(): Boolean {
        return validateInput()
    }
}