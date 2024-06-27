package com.example.android_review03_tedmoon.fragment

import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_review03_tedmoon.databinding.FragmentSub2Binding
import com.example.android_review03_tedmoon.model.ScoreInfo
import com.example.android_review03_tedmoon.utils.FragmentName
import com.example.android_review03_tedmoon.utils.Tools
import kotlin.math.roundToInt

class SubFragment2 : Fragment() {

    private lateinit var binding: FragmentSub2Binding
    var dataList: ArrayList<ScoreInfo>? = null

    // 홀수 번째 : Total, 짝수 번째 : Average
    val scoreArray = arrayOf(0.0, 0.0, 0.0)

    /*
    [0] : 국어 총합
    [1] : 영어 총합
    [2] : 수학 총합
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSub2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 기초 데이터 받아오기
        initData()
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    // 기초 데이터 받아오기
    fun initData() {
        // argumenets에서 bundle을 받아온다
        val bundle = arguments
        // Bundle 객체에서 key값을 이용하여 ArrayList를 뽑아서 dataList에 담아준다
        dataList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // API 33이상인 경우 사용하는 코드
            bundle?.getParcelableArrayList("dataList", ScoreInfo::class.java)
        } else { // API 33 미만에서 사용하는 코드
            @Suppress("DEPRECATION")
            bundle?.getParcelableArrayList("dataList")
        }
        // Data 전송 test
    }

    // View 설정
    fun settingView() {
        // 인원 수 구하기
        val num = dataList?.size?: 1 // dataList의 크기를 반환하고 dataList가 null이면 1을 반환한다
        // 과목별 총점 구하기
        settingTotalAndAverage()
        // 전체 총점 구하기
        val total = scoreArray.sum()

        // textView에 연결
        binding.apply {
            // 총점 설정
            textViewSub2KoreanTotal.text = "국어 총점 : ${scoreArray[0]}점"
            textViewSub2EnglishTotal.text = "영어 총점 : ${scoreArray[1]}점"
            textViewSub2MathTotal.text = "수학 총점 : ${scoreArray[2]}점"

            // 평균 설정
            textViewSub2KoreanAverage.text = "국어 평균 : ${((scoreArray[0] / num) * 100.0).roundToInt() / 100.0}점"
            textViewSub2EnglishAverage.text = "영어 평균 : ${((scoreArray[1] / num) * 100.0).roundToInt() / 100.0}점"
            textViewSub2MathAverage.text = "수학 평균 : ${((scoreArray[2] / num) * 100.0).roundToInt() / 100.0}점"

            // 전체 총점 설정
            textViewSub2WholeTotal.text = "전체 총점 : ${total}점"

            // 전체 평균 설정
            textViewSub2WholeAverage.text = "전체 평균 : ${((total / num) * 100.0).roundToInt() / 100.0}점"

        }

    }

    // Event 설정
    fun settingEvent() {
        binding.apply {
            buttonSub2Complete.setOnClickListener {
                // 확인 버튼 클릭 시 MainFragment로 이동한다
                removeFragment()
            }
        }

    }

    // 총점과 평균 구하기
    fun settingTotalAndAverage() {
        if (dataList != null) {
            for (item in dataList!!) {
                scoreArray[0] += item.korean
                scoreArray[1] += item.english
                scoreArray[2] += item.math
            }
        }
    }

    // 뒤로가기
    fun removeFragment(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack(FragmentName.SUB_FRAGMENT2.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}