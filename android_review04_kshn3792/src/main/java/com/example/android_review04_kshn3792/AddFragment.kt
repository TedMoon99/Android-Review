package com.example.android_review04_kshn3792

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.android_review04_kshn3792.databinding.FragmentAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.addViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
        // 입력 요소 초기화
        settingInput()
    }

    // View 설정
    fun settingView() {
        // toolbar 설정
        binding.apply {
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_add)
            }
        }
        // 에러 설정
        settingError()
    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            toolBarAdd.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로가기
                    removeFragment()
                }
                // menu
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuitem_add_save -> {
                            // 유효성 검사
                            val result = validateInput()

                            if (result) {
                                saveData()
                                // 뒤로가기
                                removeFragment()
                            }

                        }
                    }
                    true
                }
            }
        }
    }

    // 입력 요소 초기화
    fun settingInput() {
        viewModel.clearText()
    }


    // 데이터 저장
    fun saveData() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                // 입력 요소를 가져온다
                val name = viewModel.studentName.value ?: ""
                val grade = viewModel.studentGrade.value!!.toInt()
                val kor = viewModel.studentKor.value!!.toDouble()
                val eng = viewModel.studentEng.value!!.toDouble()
                val math = viewModel.studentMath.value!!.toDouble()

                // 저장할 데이터를 만든다
                val data = StudentInfo(name, grade, kor, eng, math)

                // DB에 전송한다
                val sequence = withContext(Dispatchers.IO) {
                    //DAO 함수 호출
                    AddDao.getSequence()
                }
                Log.d("test1234", "${sequence}")


            }

        } catch (e: Exception) {
            Log.e("AddFragment", "${e.message}")
        }

    }

    // 유효성 검사
    fun validateInput(): Boolean {
        // 입력 요소를 가져온다
        val name = viewModel.studentName.value ?: ""
        val grade = viewModel.studentGrade.value ?: ""
        val kor = viewModel.studentKor.value ?: ""
        val eng = viewModel.studentEng.value ?: ""
        val math = viewModel.studentMath.value ?: ""

        // 이름
        if (name.isEmpty() || name.length < 2 || name.length > 5) {
            return false
        }

        // 학년
        if (grade.isEmpty() || grade.toIntOrNull() == null || grade.toInt() < 1 || grade.toInt() > 6) {
            return false
        }

        // 국어
        if (kor.isEmpty() || kor.toDoubleOrNull() == null || kor.toDouble() < 0.0 || kor.toDouble() > 100.0) {
            return false
        }

        // 영어
        if (eng.isEmpty() || eng.toDoubleOrNull() == null || eng.toDouble() < 0.0 || eng.toDouble() > 100.0) {
            return false
        }

        // 수학
        if (math.isEmpty() || math.toDoubleOrNull() == null || math.toDouble() < 0.0 || math.toDouble() > 100.0) {
            return false
        }

        return true
    }

    // 에러 설정
    fun settingError() {
        binding.apply {
            // 이름
            viewModel.studentName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..5) {
                        editTextAddName.error = null
                    } else {
                        editTextAddName.error = "이름은 4자 이하로 입력해 주세요"
                    }
                } else {
                    editTextAddName.error = null
                }
            }
            // 학년
            viewModel.studentGrade.observe(viewLifecycleOwner) { grade ->
                if (grade != null && grade.isNotEmpty()) {
                    if (grade.toInt() in 1..6) {
                        editTextAddGrade.error = null
                    } else {
                        editTextAddGrade.error = "학년은 1~6 사이의 학년을 입력해 주세요"
                    }
                } else {
                    editTextAddGrade.error = null
                }
            }
            // 국어 점수
            viewModel.studentKor.observe(viewLifecycleOwner) { kor ->
                if (kor != null && kor.isNotEmpty()) {
                    if (kor.toDouble() in 0.0..100.0) {
                        editTextAddKorean.error = null
                    } else {
                        editTextAddKorean.error = "국어 점수는 0점에서 100점 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAddKorean.error = null
                }
            }
            // 영어 점수
            viewModel.studentEng.observe(viewLifecycleOwner) { eng ->
                if (eng != null && eng.isNotEmpty()) {
                    if (eng.toDouble() in 0.0..100.0) {
                        editTextAddEnglish.error = null
                    } else {
                        editTextAddEnglish.error = "영어 점수는 0점에서 100점 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAddEnglish.error = null
                }
            }
            // 수학 점수
            viewModel.studentMath.observe(viewLifecycleOwner) { math ->
                if (math != null && math.isNotEmpty()) {
                    if (math.toDouble() in 0.0..100.0) {
                        editTextAddMath.error = null
                    } else {
                        editTextAddMath.error = "수학 점수는 0점에서 100점 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAddMath.error = null
                }
            }
        }
    }


    // 뒤로 가기 설정
    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

}