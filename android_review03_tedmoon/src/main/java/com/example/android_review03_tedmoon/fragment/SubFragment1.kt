package com.example.android_review03_tedmoon.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.android_review03_tedmoon.databinding.FragmentSub1Binding
import com.example.android_review03_tedmoon.model.ScoreInfo
import com.example.android_review03_tedmoon.utils.FragmentName

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

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    // View 설정
    fun settingView(){
        binding.apply {

        }
    }
    // Event 설정
    fun settingEvent(){
        binding.apply {
            buttonSub1Complete.setOnClickListener {
                val resultCheck = validateInput()

                if (resultCheck){
                    // 입력을 받아온다
                    val name = textInputEditTextSub1Name.text.toString()
                    val grade = textInputEditTextSub1Grade.text.toString().toInt()
                    val korean = textInputEditTextSub1Korean.text.toString().toDouble()
                    val english = textInputEditTextSub1English.text.toString().toDouble()
                    val math = textInputEditTextSub1Math.text.toString().toDouble()

                    // ScoreInfo 객체를 만들어준다
                    val data = ScoreInfo(name, grade, korean, english, math)

                    val bundle = Bundle().apply {
                        putParcelable("scoreInfo", data)
                    }
                    // MainFragment로 돌아가면서 전달할 데이터 설정 -> MainFragment가 STARTED 상태가 될 때 result를 받아온다
                    setFragmentResult("Input Complete", bundle)

                    // Fragment를 종료한다
                    removeFragment()
                }
            }
        }
    }
    // 유효성 검사
    fun validateInput(): Boolean{
        var resultName = false
        var resultGrade = false
        var resultKorean = false
        var resultEnglish = false
        var resultMath = false

        binding.apply {
            // 입력을 받아온다
            val name = textInputEditTextSub1Name.text.toString()
            val grade = textInputEditTextSub1Grade.text.toString().toInt()
            val korean = textInputEditTextSub1Korean.text.toString().toDouble()
            val english = textInputEditTextSub1English.text.toString().toDouble()
            val math = textInputEditTextSub1Math.text.toString().toDouble()

            // 학생 이름 유효성 검사
            if (name.isNotEmpty()){
                if (name.length in 2 .. 5){
                    textInputLayoutSub1Name.error = null
                    resultName = true
                } else {
                    textInputLayoutSub1Name.error = "이름은 두 글자에서 5글자로 입력해주세요"
                    resultName = false
                }
            } else {
                textInputLayoutSub1Name.error = "이름을 입력해주세요"
                resultName = false
            }

            // 학년 이름 유효성 검사
            if (grade != 0){
                if (grade in 1 .. 6){
                    textInputLayoutSub1Grade.error = null
                    resultGrade = true
                } else {
                    textInputLayoutSub1Grade.error = "학년은 1학년에서 6학년 사이로 입력해주세요"
                    resultGrade = false
                }
            } else {
                textInputLayoutSub1Grade.error = "학년을 입력해주세요"
                resultGrade = false
            }

            // 국어 점수 유효성 검사
            if (!korean.isNaN()){
                if (korean in 0.0 .. 100.0){
                    textInputLayoutSub1Korean.error = null
                    resultKorean = true
                } else {
                    textInputLayoutSub1Korean.error = "국어 점수는 0점에서 100점 사이의 값을 입력해주세요"
                    resultKorean = false
                }
            } else {
                textInputLayoutSub1Korean.error = "국어 점수를 입력해주세요"
                resultKorean = false
            }

            // 영어 점수 유효성 검사
            if (!english.isNaN()){
                if (english in 0.0 .. 100.0){
                    textInputLayoutSub1English.error = null
                    resultEnglish = true
                } else {
                    textInputLayoutSub1English.error = "영어 점수는 0점에서 100점 사이의 값을 입력해주세요"
                    resultKorean = false
                }
            } else {
                textInputLayoutSub1English.error = "영어 점수를 입력해주세요"
                resultEnglish = false
            }

            // 수학 점수 유효성 검사
            if (!math.isNaN()){
                if (math in 0.0 .. 100.0){
                    textInputLayoutSub1Math.error = null
                    resultMath = true
                } else {
                    textInputLayoutSub1Math.error = "수학 점수는 0점에서 100점 사이의 값을 입력해주세요"
                    resultMath = false
                }
            } else {
                textInputLayoutSub1Math.error = "수힉 점수를 입력해주세요"
                resultMath = false
            }

        }

        return resultName && resultGrade && resultKorean && resultEnglish && resultMath
    }

    // 뒤로가기
    fun removeFragment(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack(FragmentName.SUB_FRAGMENT1.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}