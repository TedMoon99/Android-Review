package com.example.android_review04_baek08102

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlin.math.* // round 사용 목적
import com.example.android_review04_baek08102.databinding.ActivityMainBinding
import com.example.android_review04_baek08102.databinding.FragmentMainBinding
import com.example.android_review04_baek08102.databinding.FragmentSub3Binding

class SubFragment3 : Fragment() {
    private lateinit var binding: FragmentSub3Binding
    private var data: StudentData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSub3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingData()
        settingView()
        settingEvent()
    }

    fun settingView() {
        binding.apply {
            sub3ToolBar.apply {
                title = "학생 정보 열람"
                setNavigationIcon(R.drawable.arrow_back_24px)
            }
            //
            val total = (data?.koreanScore ?: 0.0) + (data?.englishScore ?: 0.0) + (data?.mathScore ?: 0.0)
            val average = total / 3.0

            // 전달받은 데이터 출력
            sub3TextViewName.text = "학생 이름 : ${data?.name.toString()}"
            sub3TextViewGrade.text = "학년 : ${data?.grade.toString()}학년"
            sub3TextViewKoreanScore.text = "국어 점수 : ${data?.koreanScore.toString()}점"
            sub3TextViewEnglishScore.text = "영어 점수 : ${data?.englishScore.toString()}점"
            sub3TextViewMathScore.text = "수학 점수 : ${data?.mathScore.toString()}점"

            sub3TextViewTotal.text = "총점 : ${total}점 "
            sub3TextViewAverage.text = "평균 : ${round(average * 10) / 10}점"
        }
    }

    fun settingEvent() {
        binding.apply {
            sub3ToolBar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }
            }
        }
    }

    // 전달받은 데이터 세팅 함수
    fun settingData() {
        val bundle = arguments // arguments로 전달한 bundle 받아서
        // API 레벨 호환성 고려
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable("studentInform", StudentData::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle?.getParcelable("studentInform")
        } // result가 null이 아닐 시
        result?.let {
            data = result // data 변수에 정보 담음

            Log.d("test1", "fragment3 - data handled $data")
        }
    }

    fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.SUB_FRAGMENT3.name,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}