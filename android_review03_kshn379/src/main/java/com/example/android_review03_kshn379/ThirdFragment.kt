package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_review03_kshn379.databinding.FragmentThirdBinding
import kotlin.math.roundToInt

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding
    var dataList: ArrayList<Student>? = null

    // 홀수 번째: Total, 짝수 번째: Average
    val scoreArray = arrayOf(0.0, 0.0, 0.0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // arguments 로부터 studentList 불러 오기
        dataList = arguments?.getParcelableArrayList("studentList")

        // 데이터가 없으면 return
        if (dataList == null) {
            return
        }

        // View 설정
        settingView()
        // Event 설정
        settingEvent()


    }

    // NaN(Not-a-Number) 확인
    // NaN이 아닌 경우 소수점 둘째 자리 반올림 반환
    // NaN인 경우 0.0 반환
    private fun formatAverage(score: Double, num: Int): Double {
        val average = score / num
        return if (average.isNaN()) 0.0 else (average * 100.0).roundToInt() / 100.0
    }



    fun settingView() {
    binding.apply {
            // 인원 수 구하기
            val num = dataList?.size ?: 1
            // 과목별 총점 구하기
            settingTotalAndAverage()
            // 전체 총점 구하기
            val total = scoreArray.sum()


            korMainTotal.text = "국어 총점 : ${scoreArray[0]}점"
            engMainTotal.text = "영어 총점 : ${scoreArray[1]}점"
            mathMainTotal.text = "수학 총점 : ${scoreArray[2]}점"

            korMainAverage.text = "국어 평균 : ${formatAverage(scoreArray[0], num)}점"
            engMainAverage.text = "영어 평균 : ${formatAverage(scoreArray[1], num)}점"
            mathMainAverage.text = "수학 평균 : ${formatAverage(scoreArray[2], num)}점"

            allMainTotal.text = "전체 총점 : ${total}점"
            allMainAverage.text = "전체 평균 : ${formatAverage(total, num)}점"

        }
            }

    fun settingEvent() {
        binding.apply {
            // 버튼 클릭 시 메인 화면 전환
            checkMainScore.setOnClickListener{
                parentFragmentManager.popBackStack()
            }
        }
    }

    fun settingTotalAndAverage() {
        // 배열 초기화
        scoreArray[0] = 0.0
        scoreArray[1] = 0.0
        scoreArray[2] = 0.0

        dataList?.let { students ->
            for (student in students) {
                scoreArray[0] += student.kor
                scoreArray[1] += student.eng
                scoreArray[2] += student.math
            }
        }
    }

        }