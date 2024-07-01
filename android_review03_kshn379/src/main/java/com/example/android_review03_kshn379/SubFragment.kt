package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_review03_kshn379.databinding.FragmentSubBinding

class SubFragment : Fragment() {

    lateinit var binding: FragmentSubBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()


    }

    fun settingView() {
        binding.apply {
            studentMainButton.setOnClickListener {
                // 데이터 입력 및 출력
                val studentMainName = binding.studentMainName.text.toString()
                val studentMainGrade = binding.studentMainGrade.text.toString().toIntOrNull()
                val koreanMainScore = binding.koreanMainScore.text.toString().toIntOrNull()
                val mathMainScore = binding.mathMainScore.text.toString().toIntOrNull()
                val englishMainScore = binding.englishMainScore.text.toString().toIntOrNull()

                // 총점 및 평균 데이터 입력 및 출력
                if (studentMainName.isNotEmpty() && studentMainGrade != null && koreanMainScore != null && englishMainScore != null && mathMainScore != null) {
                    val total = koreanMainScore + englishMainScore + mathMainScore
                    val average = total / 3

                    val mainFragment = parentFragmentManager.findFragmentByTag("MainFragment") as? MainFragment
                    mainFragment?.saveStudentData(studentMainName, studentMainGrade,
                        koreanMainScore.toDouble(),
                        englishMainScore.toDouble(), mathMainScore.toDouble(),
                        total.toDouble(), average.toDouble()
                    )
                    parentFragmentManager.popBackStack()
                }
                }

            }


        }

    }