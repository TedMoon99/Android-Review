package com.example.android_review03_baek08102

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review03_baek08102.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private val viewModel: CustomViewModel by activityViewModels()

    // 바인딩 관련
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater)

        return binding.root
    }

    // 일반 함수 호출
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingEvent()

    }

    fun settingEvent() {

        // 버튼 클릭 이벤트
        binding.apply {

            // 버튼 클릭 시
            inputButtonConfirm.setOnClickListener {

                val result = validateInput()

                // 유효성 검사 진행 후
                if (result) {
                    val name = inputTextInputEdittextName.text.toString()
                    val grade = inputTextInputEdittextGrade.text.toString().toInt()
                    val korean = inputTextInputEdittextKoreanScore.text.toString().toDouble()
                    val english = inputTextInputEdittextEnglishScore.text.toString().toDouble()
                    val math = inputTextInputEdittextMathScore.text.toString().toDouble()

                    // viewModel 내에 선언한 함수로 데이터 입력
                    val student = StudentData(name, grade, korean, english, math)
                    viewModel.getData(student)

                    Log.d(
                        "test1234",
                        "data : ${viewModel.studentList.value}}"
                    )

                    parentFragmentManager.popBackStack(
                        FragmentName.INPUT_FRAGMENT.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
    }


    // 유효성 검사
    fun validateInput(): Boolean {

        // 각 입력 항목에 대한 Boolean 타입 반환값
        var resultName = false
        var resultGrade = false
        var resultKoreanScore = false
        var resultEnglishScore = false
        var resultMathScore = false

        binding.apply {

            // 이름 항목에 대한 유효성 검사
            val name = inputTextInputEdittextName.text.toString()
            if (name.isNotEmpty()) {                      // 입력값이 비어있지 않고
                if (name.length in 2 until 6) {     // 입력 문자열 길이가 2자 이상 6자 미만 일시
                    inputTextInputLayoutName.error = null // 에러메시지 초기화
                    resultName = true                     // name 입력값에 대해 true 반환
                } else {
                    inputTextInputLayoutName.error = "올바른 이름 형식이 아닙니다"
                    resultName = false
                }
            } else {
                inputTextInputLayoutName.error = "값을 입력해주세요"
                resultName = false
            }

            // 학년 항목에 대한 유효성 검사
            val grade = inputTextInputEdittextGrade.text.toString()
            if (grade.isNotEmpty()) {
                if (grade.toInt() in 1..6) {
                    inputTextInputLayoutGrade.error = null
                    resultGrade = true
                } else {
                    inputTextInputLayoutGrade.error = "올바른 학년 형식이 아닙니다"
                    resultGrade = false
                }
            } else {
                inputTextInputLayoutGrade.error = "값을 입력해주세요"
                resultGrade = false
            }

            // 국어 점수 항목에 대한 유효성 검사
            val koreanScore = inputTextInputEdittextKoreanScore.text.toString()
            if (koreanScore.isNotEmpty()) {
                if (koreanScore.toFloat() in (0.0..100.0)) {
                    inputTextInputLayoutKoreanScore.error = null
                    resultKoreanScore = true
                } else {
                    inputTextInputLayoutKoreanScore.error = "올바른 점수 형식이 아닙니다"
                    resultKoreanScore = false
                }
            } else {
                inputTextInputLayoutKoreanScore.error = "값을 입력해주세요"
                resultKoreanScore = false
            }

            // 영어 점수 항목에 대한 유효성 검사
            val englishScore = inputTextInputEdittextEnglishScore.text.toString()
            if (englishScore.isNotEmpty()) {
                if (englishScore.toFloat() in (0.0..100.0)) {
                    inputTextInputLayoutEnglishScore.error = null
                    resultEnglishScore = true
                } else {
                    inputTextInputLayoutEnglishScore.error = "올바른 점수 형식이 아닙니다"
                    resultEnglishScore = false
                }
            } else {
                inputTextInputLayoutEnglishScore.error = "값을 입력해주세요"
                resultEnglishScore = false
            }

            // 수학 점수 항목에 대한 유효성 검사
            val mathScore = inputTextInputEdittextMathScore.text.toString()
            if (mathScore.isNotEmpty()) {
                if (mathScore.toFloat() in (0.0..100.0)) {
                    inputTextInputLayoutMathScore.error = null
                    resultMathScore = true
                } else {
                    inputTextInputLayoutMathScore.error = "올바른 점수 형식이 아닙니다"
                    resultMathScore = false
                }
            } else {
                inputTextInputLayoutMathScore.error = "값을 입력해주세요"
                resultMathScore = false
            }

            // 모든 입력값 유효성 검사 통과 시 true 반환
            return resultName && resultGrade && resultKoreanScore && resultEnglishScore && resultMathScore
        }
    }
}