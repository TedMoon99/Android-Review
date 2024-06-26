package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_review03_kshn379.databinding.FragmentMainBinding
import com.example.android_review03_kshn379.databinding.FragmentSubBinding

class SubFragment : Fragment() {

    lateinit var binding: FragmentSubBinding
    lateinit var mainFragment: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()

        settingEvent()

    }

    fun settingView() {
        binding.apply {
            studentMainButton.setOnClickListener {

                // 학생 이름 가져오기 및 문자열로 변환
                val studentMainName = studentMainName.text.toString()

                // 학년 가져오기 및 정수로 변환
                val studentMainGrade = studentMainGrade.text.toString().toIntOrNull()
                val koreanMainScore = koreanMainScore.text.toString().toIntOrNull()
                val englishMainScore = englishMainScore.text.toString().toIntOrNull()
                val mathMainScore = mathMainScore.text.toString().toIntOrNull()


                val bundle = Bundle()
                bundle.putString("학생 이름", studentMainName)
                if (studentMainGrade != null) {
                    bundle.putInt("학년", studentMainGrade)
                }
                if (koreanMainScore != null) {
                    bundle.putInt("국어 점수", koreanMainScore)
                }
                if (englishMainScore != null) {
                    bundle.putInt("영어 점수", englishMainScore)
                }
                if (mathMainScore != null) {
                    bundle.putInt("수학 점수", mathMainScore)

                    parentFragmentManager.popBackStack("SubFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                }
//                mainFragment.rvMainList
            }
        }


    }



    fun settingEvent() {

    }
}