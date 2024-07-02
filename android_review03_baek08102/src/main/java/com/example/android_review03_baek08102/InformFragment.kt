package com.example.android_review03_baek08102

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review03_baek08102.databinding.FragmentInformBinding
import com.example.android_review03_baek08102.databinding.FragmentInputBinding
import kotlin.math.*

class InformFragment : Fragment() {

    private lateinit var binding: FragmentInformBinding
    private val viewModel: CustomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInformBinding.inflate(inflater)

        return binding.root
    }

    // 일반 함수 선언
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
    }

    // 이벤트 설정 함수
    fun settingEvent() {
        binding.informButtonConfirm.setOnClickListener { // 버튼 클릭 시
            parentFragmentManager.popBackStack(                  // backstack 제거,
                FragmentName.INFORM_FRAGMENT.name,               // 현재 프래그먼트
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    fun settingView() {
        binding.apply {

            settingInform(viewModel.adapterPosition.value)

        }
    }

    fun settingInform(position: Int?) {
        binding.apply {
            // viewModel 리스트 받아와서
            val studentInform: List<StudentData>? = viewModel.studentList.value

            // position이 null이 아닐 시
            if (position != null) {
                // adapter에서 viewModel에 넘겨준 position 가져와서
                studentInform?.get(position)?.apply {

                    // viewModel에 선언된 studentList의 position 번째 index의 항목 적용, 출력
                    val studentTotal = koreanScore + englishScore + mathScore
                    val studentAverage = studentTotal / 3

                    informTextViewName.text = name
                    informTextViewGrade.text = "${grade} 학년"
                    informTextViewKoreanScore.text = "국어 점수 : ${koreanScore} 점"
                    informTextViewEnglishScore.text = "영어 점수 : ${englishScore} 점"
                    informTextViewMathScore.text = "수학 점수 : ${mathScore} 점"
                    informTextViewTotal.text = "총점 : ${studentTotal} 점"
                    informTextViewAverage.text = "평균 : ${round(studentAverage * 10) / 10} 점"
                }
            }
        }
    }
}