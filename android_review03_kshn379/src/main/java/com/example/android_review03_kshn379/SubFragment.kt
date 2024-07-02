package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_review03_kshn379.databinding.FragmentSubBinding
import kotlin.math.roundToInt

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
                val resultCheck = validateInput()

                if (resultCheck) {
                    // 데이터 입력 및 출력
                    val studentMainName = binding.studentMainName.text.toString()
                    val studentMainGrade = binding.studentMainGrade.text.toString().toIntOrNull()
                    val koreanMainScore =
                        binding.koreanMainScore.text.toString().toDouble().roundToInt()
                    val mathMainScore =
                        binding.mathMainScore.text.toString().toDouble().roundToInt()
                    val englishMainScore =
                        binding.englishMainScore.text.toString().toDouble().roundToInt()

                    // 총점 및 평균 데이터 입력 및 출력
                    if (studentMainName.isNotEmpty() && studentMainGrade != null && koreanMainScore != null && englishMainScore != null && mathMainScore != null) {
                        val total =
                            (koreanMainScore + englishMainScore + mathMainScore).toDouble()
                                .roundToInt()
                        val average = (total / 3).toDouble().roundToInt()


                        val mainFragment =
                            parentFragmentManager.findFragmentByTag("MainFragment") as? MainFragment
                        mainFragment?.saveStudentData(
                            studentMainName, studentMainGrade,
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


        // 유효성 검사
        fun validateInput(): Boolean {
            var resultName = false
            var resultGrade = false
            var resultKorean = false
            var resultEnglish = false
            var resultMath = false

            binding.apply {
                val name = studentMainName.text.toString()
                val grade = studentMainGrade.text.toString().toIntOrNull()
                val korean = koreanMainScore.text.toString().toDoubleOrNull()
                val english = englishMainScore.text.toString().toDoubleOrNull()
                val math = mathMainScore.text.toString().toDoubleOrNull()

                if (name.isNotEmpty()) {
                    studentMainName.error = null
                    resultName = true
                } else {
                    studentMainName.error = "이름을 입력해주세요."
                }

                if (grade != 0 && grade != null) {
                    studentMainGrade.error = null
                    resultGrade = true
                } else {
                    studentMainGrade.error = "학년을 입력해주세요."
                }

                if (korean != null) {
                    koreanMainScore.error = null
                    resultKorean = true
                } else {
                    koreanMainScore.error = "국어 점수를 입력해주세요."
                }

                if (english != null) {
                    englishMainScore.error = null
                    resultEnglish = true
                } else {
                    englishMainScore.error = "영어 점수를 입력해주세요."
                }
                if (math != null) {
                    mathMainScore.error = null
                    resultMath = true
                } else {
                    mathMainScore.error = "수학 점수를 입력해주세요."
                }
            }
            return resultName && resultGrade && resultKorean && resultEnglish && resultMath
        }
    }
