package com.example.android_review04_baek08102

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult

import com.example.android_review04_baek08102.databinding.ActivityMainBinding
import com.example.android_review04_baek08102.databinding.FragmentMainBinding
import com.example.android_review04_baek08102.databinding.FragmentSub1Binding

class SubFragment1 : Fragment() {
    private lateinit var binding: FragmentSub1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSub1Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
    }

    // 화면 관련
    fun settingView() {
        binding.apply {
            // 툴바 세팅
            sub1ToolBar.apply {
                setTitle("학생 정보 입력")
                setNavigationIcon(R.drawable.arrow_back_24px)
                inflateMenu(R.menu.menu_confirm)
            }
        }
    }

    // 이벤트 관련
    fun settingEvent() {
        binding.apply {
            // 툴바 관련 이벤트 설정
            sub1ToolBar.apply {
                // 네비게이션 아이콘 클릭 시
                setNavigationOnClickListener {

                    removeFragment() // 현재 프래그먼트 백스택에서 제거
                }
                // 메뉴 아이템 클릭 시
                setOnMenuItemClickListener { itemName ->
                    when (itemName.itemId) {
                        R.id.menu_confirm -> {
                            val result = validateInput()

                            if (result) { // 입력 유효성 검사 통과 시
                                // 입력받은 각 데이터 불러오고
                                val name = sub1EditTextName.text.toString()
                                val grade = sub1EditTextGrade.text.toString().toInt()
                                val koreanScore = sub1EditTextKoreanScore.text.toString().toDouble()
                                val englishScore = sub1EditTextEnglishScore.text.toString().toDouble()
                                val mathScore = sub1EditTextMathScore.text.toString().toDouble()
                                // 데이터 클래스 형식으로 담아
                                val data =
                                    StudentData(name, grade, koreanScore, englishScore, mathScore)

                                val bundle = Bundle().apply {
                                    putParcelable("inputData", data) // 번들에 담아
                                }

                                setFragmentResult("Done Input", bundle) // bundle과 key값 함께 전달

                                removeFragment() // 현재 프래그먼트 백스택에서 제거
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    // 입력값 유효성 검사 함수
    fun validateInput(): Boolean { // 반환 타입 boolean
        // 각 입력 데이터별 검사 결과값 초기화
        var resultName = false
        var resultGrade = false
        var resultKoreanScore = false
        var resultEnglishScore = false
        var resultMathScore = false

        binding.apply {
            // 검사하려는 데이터 타입에 따라 초기 조건 설정

            val name = sub1EditTextName.text.toString()
            // string 타입이므로 추가적인 설정 X
            val grade = sub1EditTextGrade.text.toString().toIntOrNull() ?: 0
            // 입력값이 없거나 숫자가 아닌 경우 null 반환 -> 엘비스 연산자 통하여 0으로 설정
            val koreanScore = sub1EditTextKoreanScore.text.toString().toDoubleOrNull() ?: Double.NaN
            // 입력값이 없거나 숫자가 아닌 경우 null 반환 -> 엘비스 연산자 통하여 Double.Nan으로 설정
            val englishScore = sub1EditTextEnglishScore.text.toString().toDoubleOrNull() ?: Double.NaN
            val mathScore = sub1EditTextMathScore.text.toString().toDoubleOrNull() ?: Double.NaN

            if (name.isNotEmpty()) {
                if (name.length in 2..5) {
                    sub1TextInputLayoutName.error = null
                    resultName = true
                } else {
                    sub1TextInputLayoutName.error = "이름은 두 자에서 다섯 자까지 입력할 수 있습니다"
                }
            } else {
                sub1TextInputLayoutName.error = "이름을 입력해주세요"
            }

            if (grade != 0) {
                if (grade in 1..6) {
                    sub1TextInputLayoutGrade.error = null
                    resultGrade = true
                } else {
                    sub1TextInputLayoutGrade.error = "학년은 1학년에서 6학년까지 입력할 수 있습니다"
                }
            } else {
                sub1TextInputLayoutGrade.error = "학년을 입력해주세요"
            }

            if (!koreanScore.isNaN()) {
                if (koreanScore in 0.0..100.0) {
                    sub1TextInputLayoutKoreanScore.error = null
                    resultKoreanScore = true
                } else {
                    sub1TextInputLayoutKoreanScore.error = "점수는 0점에서 100점까지 입력할 수 있습니다"
                }
            } else {
                sub1TextInputLayoutKoreanScore.error = "점수를 입력해주세요"
            }

            if (!englishScore.isNaN()) {
                if (englishScore in 0.0..100.0) {
                    sub1TextInputLayoutEnglishScore.error = null
                    resultEnglishScore = true
                } else {
                    sub1TextInputLayoutEnglishScore.error = "점수는 0점에서 100점까지 입력할 수 있습니다"
                }
            } else {
                sub1TextInputLayoutEnglishScore.error = "점수를 입력해주세요"
            }

            if (!mathScore.isNaN()) {
                if (mathScore in 0.0..100.0) {
                    sub1TextInputLayoutMathScore.error = null
                    resultMathScore = true
                } else {
                    sub1TextInputLayoutMathScore.error = "점수는 0점에서 100점까지 입력할 수 있습니다"
                }
            } else {
                sub1TextInputLayoutMathScore.error = "점수를 입력해주세요"
            }
        }

        return resultName && resultGrade && resultKoreanScore && resultEnglishScore && resultMathScore
    }

    // 현재 프래그먼트 백스택에서 제거하는 함수
    fun removeFragment() {
        parentFragmentManager
            .popBackStack(FragmentName.SUB_FRAGMENT1.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}