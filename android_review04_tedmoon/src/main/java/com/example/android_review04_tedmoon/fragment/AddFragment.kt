package com.example.android_review04_tedmoon.fragment

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review04_tedmoon.R
import com.example.android_review04_tedmoon.dao.ScoreDao
import com.example.android_review04_tedmoon.databinding.FragmentAddBinding
import com.example.android_review04_tedmoon.model.ScoreInfo
import com.example.android_review04_tedmoon.utils.FragmentName
import com.example.android_review04_tedmoon.viewmodel.AddViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    val viewModel: AddViewModel by activityViewModels()
    lateinit var Fcontext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Fcontext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.addViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
        // 입력요소 초기화
        settingInputUI()
    }

    // View 설정
    fun settingView() {
        // 에러 설정
        settingError()
        // Toolbar 설정
        settingToolbar()
    }

    // Toolbar 설정
    fun settingToolbar() {
        binding.toolbarAdd.apply {
            // menu 적용
            inflateMenu(R.menu.menu_add)
        }
    }

    // Event 설정
    fun settingEvent() {
        // Toolbar
        binding.toolbarAdd.apply {
            // 뒤로가기
            setNavigationOnClickListener {
                // 화면 종료
                removeFragment()
            }
            // 확인 처리
            setOnMenuItemClickListener {
                when (it.itemId) {
                    // 확인 메뉴
                    R.id.menuItem_add_complete -> {
                        val result = validateInput()

                        if (result) {
                            // 유효성 결과가 true => DB에 정보 저장
                            saveData()
                            // 화면 종료
                            removeFragment()
                        }
                    }
                }
                true
            }
        }
    }
    // 입력 요소 초기화
    fun settingInputUI(){
        viewModel.clearText()
    }

    // 입력 완료 처리 함수
    fun saveData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // scoreIdx 불러오기
                val studentSequence = withContext(Dispatchers.IO) { ScoreDao.getSequence() }
                // DB에 Sequence를 업데이트 해준다
                withContext(Dispatchers.IO) { ScoreDao.updateSequence(studentSequence + 1) }

                // index 저장
                val studentIdx = studentSequence + 1

                // 입력요소 값 불러오기
                val name = viewModel.studentName.value!!
                val grade = viewModel.studentGrade.value!!.toInt()
                val korean = viewModel.studentKorean.value!!.toDouble()
                val english = viewModel.studentEnglish.value!!.toDouble()
                val math = viewModel.studentMath.value!!.toDouble()

                // 저장할 데이터를 객체에 담는다
                val data = ScoreInfo(studentIdx, name, grade, korean, english, math)

                // 사용자 정보를 저장한다
                withContext(Dispatchers.IO) { ScoreDao.insertStudentData(data) }

                Toast.makeText(Fcontext, "데이터 저장 성공", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(Fcontext, "데이터 저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 유효성 검사
    fun validateInput(): Boolean {
        val name = viewModel.studentName.value ?: ""
        val grade = viewModel.studentGrade.value ?: ""
        val korean = viewModel.studentKorean.value ?: ""
        val english = viewModel.studentEnglish.value ?: ""
        val math = viewModel.studentMath.value ?: ""

        // 이름
        if (name.isEmpty() || name.length < 2 || name.length > 5) {
            return false
        }

        // 학년
        if (grade.isEmpty() || grade.toIntOrNull() == null || grade.toInt() < 1 || grade.toInt() > 6) {
            return false
        }

        // 국어 점수
        if (korean.isEmpty() || korean.toDoubleOrNull() == null || korean.toDouble() < 0.0 || korean.toDouble() > 100.0) {
            return false
        }

        // 영어 점수
        if (english.isEmpty() || english.toDoubleOrNull() == null || english.toDouble() < 0.0 || english.toDouble() > 100.0) {
            return false
        }
        // 수학 점수
        if (math.isEmpty() || math.toDoubleOrNull() == null || math.toDouble() < 0.0 || math.toDouble() > 100.0) {
            return false
        }
        return true
    }

    // 입력요소 Error 설정
    fun settingError() {
        binding.apply {
            // 이름
            viewModel.studentName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..5) {
                        textInputLayoutAddName.error = null
                    } else {
                        textInputLayoutAddName.error = "이름은 두 글자 이상 다섯 글자 이하로 작성해주세요"
                    }
                } else {
                    textInputLayoutAddName.error = null
                }
            }
            // 학년
            viewModel.studentGrade.observe(viewLifecycleOwner) { grade ->
                if (grade != null && grade.isNotEmpty()) {
                    if (grade.toInt() in 1..6) {
                        textInputLayoutAddGrade.error = null
                    } else {
                        textInputLayoutAddGrade.error = "학년은 1 ~ 6의 숫자를 입력해주세요"
                    }
                } else {
                    textInputLayoutAddGrade.error = null
                }
            }
            // 국어 점수
            viewModel.studentKorean.observe(viewLifecycleOwner) { korean ->
                if (korean != null && korean.isNotEmpty()) {
                    if (korean.toDouble() in 0.0..100.0) {
                        textInputLayoutAddKorean.error = null
                    } else {
                        textInputLayoutAddKorean.error = "국어 점수는 0점에서 100점 사이의 값을 입력해주세요"
                    }
                } else {
                    textInputLayoutAddKorean.error = null
                }
            }
            // 영어 점수
            viewModel.studentEnglish.observe(viewLifecycleOwner) { english ->
                if (english != null && english.isNotEmpty()) {
                    if (english.toDouble() in 0.0..100.0) {
                        textInputLayoutAddEnglish.error = null
                    } else {
                        textInputLayoutAddEnglish.error = "영어 점수는 0점에서 100점 사이의 값을 입력해주세요"
                    }
                } else {
                    textInputLayoutAddEnglish.error = null
                }
            }
            // 수학 점수
            viewModel.studentMath.observe(viewLifecycleOwner) { math ->
                if (math != null && math.isNotEmpty()) {
                    if (math.toDouble() in 0.0..100.0) {
                        textInputLayoutAddMath.error = null
                    } else {
                        textInputLayoutAddMath.error = "수학 점수는 0점에서 100점 사이의 값을 입력해주세요"
                    }
                } else {
                    textInputLayoutAddMath.error = null
                }
            }
        }
    }

    // 뒤로가기
    fun removeFragment() {
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}
