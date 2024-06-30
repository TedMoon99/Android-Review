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


       settingView()


        binding.checkMainScore.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


    }

    fun settingView() {
    binding.apply {
        checkMainScore.setOnClickListener {
            // 인원 수 구하기
            val num = dataList?.size?: 1
            // 과목별 총점 구하기
            settingTotalAndAverage()
            // 전체 총점 구하기
            val total = scoreArray.sum()


            korMainTotal.text = "국어 총점 : ${scoreArray[0]}점"
            engMainTotal.text = "영어 총점 : ${scoreArray[1]}점"
            mathMainTotal.text = "수학 총점 : ${scoreArray[2]}점"

            korMainAverage.text = "국어 평균 : ${((scoreArray[0] / num) * 100.0).roundToInt() / 100.0}점"
            engMainAverage.text = "영어 평균 : ${((scoreArray[0] / num) * 100.0).roundToInt() / 100.0}점"
            mathMainAverage.text = "수학 평균 : ${((scoreArray[0] / num) * 100.0).roundToInt() / 100.0}점"

            allMainTotal.text = "전체 총점 : ${total}점"
            allMainAverage.text = "전체 평균 : ${((total / num) * 100.0).roundToInt() / 100.0}점"
        }
        }

            }


    fun settingTotalAndAverage() {
        if (dataList != null) {
            for (i in dataList!!.indices) {
                scoreArray[0] += dataList!![i].kor
                scoreArray[1] += dataList!![i].eng
                scoreArray[2] += dataList!![i].math
            }
        }
    }

        }