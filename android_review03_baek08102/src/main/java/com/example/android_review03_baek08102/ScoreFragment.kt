package com.example.android_review03_baek08102

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.android_review03_baek08102.databinding.FragmentInformBinding
import com.example.android_review03_baek08102.databinding.FragmentInputBinding
import com.example.android_review03_baek08102.databinding.FragmentScoreBinding
import kotlin.math.absoluteValue
import kotlin.math.*

class ScoreFragment : Fragment() {

    private lateinit var binding: FragmentScoreBinding
    private lateinit var mainActivity: MainActivity
    private val viewModel: CustomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScoreBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
    }

    fun settingView() {

        viewModel.studentList.observe(viewLifecycleOwner) { data ->

            // studentList의 size 반환, studentList가 null일시 1 반환
            val size = viewModel.studentList.value?.size ?: 1

            // viewModel 내부 함수 사용하여 각 과목 총점 구하고
            val koreanTotal = viewModel.getKoreanTotal()
            val englishTotal = viewModel.getEnglishTotal()
            val mathTotal = viewModel.getMathTotal()
            val allTotal = koreanTotal + englishTotal + mathTotal

            // 총점과 size로 과목별 평균 구한다
            val koreanAverage = koreanTotal / size
            val englishAverage = englishTotal / size
            val mathAverage = mathTotal / size
            val allAverage = (koreanAverage + englishAverage + mathAverage) / size

            // 총점 및 평균 출력
            binding.apply {
                scoreTextViewKoreanTotal.text = "국어 총점 : ${koreanTotal} 점"
                scoreTextViewEnglishTotal.text = "영어 총점 : ${englishTotal} 점"
                scoreTextViewMathTotal.text = "수학 총점 : ${mathTotal} 점"

                scoreTextViewKoreanAverage.text = "국어 평균 : ${round(koreanAverage * 10) / 10} 점"
                scoreTextViewEnglishAverage.text = "영어 평균 : ${round(englishAverage * 10) / 10} 점"
                scoreTextViewMathAverage.text = "수학 평균 : ${round(mathAverage * 10) / 10} 점"

                scoreTextViewAllTotal.text = "전체 총점 : ${allTotal} 점"
                scoreTextViewAllAverage.text = "전체 평균 : ${round(allAverage * 10) / 10} 점"
            }
        }
    }

    fun settingEvent() {
        binding.scoreButtonConfirm.setOnClickListener {
            mainActivity.switchFragment(FragmentName.MAIN_FRAGMENT)
        }
    }
}