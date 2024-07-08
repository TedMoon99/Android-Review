package com.example.android_review04_baek08102

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import com.example.android_review04_baek08102.databinding.ActivityMainBinding
import com.example.android_review04_baek08102.databinding.FragmentMainBinding
import com.example.android_review04_baek08102.databinding.FragmentSub1Binding
import com.example.android_review04_baek08102.databinding.FragmentSub2Binding
import kotlin.math.*

class SubFragment2 : Fragment() {
    private lateinit var binding: FragmentSub2Binding
    private var dataList: ArrayList<StudentData>? = arrayListOf()

    // 총점 계산할 때 사용할 변수 선언 및 초기화
    private var koreanTotal = 0.0
    private var englishTotal = 0.0
    private var mathTotal = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSub2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingData()
        settingView()
        settingEvent()
    }

    // 화면 관련
    fun settingView() {
        binding.apply {
            sub2ToolBar.apply {
                title = "총점 및 평균"
                setNavigationIcon(R.drawable.arrow_back_24px)
            }

            getTotal() // 총점 계산한 뒤
            val size = dataList!!.size
            // 총점 데이터로 평균 점수 계산
            val koreanAverage = koreanTotal / size
            val englishAverage = englishTotal / size
            val mathAverage = mathTotal / size

            // 총점 및 평균 출력
            sub2TextViewKoreanTotal.text = "국어 총점 : ${round(koreanTotal * 10) / 10} 점"
            sub2TextViewKoreanAverage.text = "국어 평균 : ${round(koreanAverage * 10) / 10} 점"
            sub2TextViewEnglishTotal.text = "영어 총점 : ${round(englishTotal * 10) / 10} 점"
            sub2TextViewEnglishAverage.text = "영어 평균 : ${round(englishAverage * 10) / 10} 점"
            sub2TextViewMathTotal.text = "수학 총점 : ${round(mathTotal * 10) / 10} 점"
            sub2TextViewMathAverage.text = "수학 평균 : ${round(mathAverage * 10) / 10} 점"
            sub2TextViewAllTotal.text = "전체 총점 : ${koreanTotal + englishTotal + mathTotal} 점"
            sub2TextViewAllAverage.text = "전체 평균 : ${round(((koreanAverage + englishAverage + mathAverage) / 3) * 10) / 10} 점"

            Log.d("bds", "checking data value ${koreanTotal}, ${mathTotal}, ${englishTotal}, size : ${size}")

        }
    }

    // 이벤트 관련
    fun settingEvent() {
        binding.apply {
            // toolBar 관련 이벤트
            sub2ToolBar.apply {
                setNavigationOnClickListener {
                    removeFragment()
                }
            }
        }
    }

    // 전달받은 데이터 세팅하는 함수
    fun settingData() {
        val bundle = arguments // arguments로 전달한 bundle 받아서
        // API 레벨 호환성 고려
        dataList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelableArrayList("studentTotal", StudentData::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle?.getParcelableArrayList("studentTotal")
        } // dataList에 입력

        Log.d("bds", "bundle receive bundle:${bundle}, dataList: ${dataList}")
    }

    // 총점 구하는 함수
    fun getTotal() {
        var loop = 1
        if (dataList != null) { // dataList가 비어있지 않다면
            for (item in dataList!!) { // 각 item 값의 합 연산
                koreanTotal += item.koreanScore
                englishTotal += item.englishScore
                mathTotal += item.mathScore

            }
        }
    }

    // 현재 프래그먼트 백스택에서 제거하는 함수
    fun removeFragment() {
        parentFragmentManager
            .popBackStack(FragmentName.SUB_FRAGMENT2.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}