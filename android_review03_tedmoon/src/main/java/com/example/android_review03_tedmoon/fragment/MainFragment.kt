package com.example.android_review03_tedmoon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_review03_tedmoon.R
import com.example.android_review03_tedmoon.databinding.FragmentMainBinding
import com.example.android_review03_tedmoon.utils.FragmentName

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    fun settingEvent(){
        binding.apply {
            // 학생 정보 입력 버튼 리스너 설정
            buttonMainAdd.setOnClickListener {
                parentFragmentManager.apply {
                    beginTransaction() // 트랜잭션 생성
                        .replace(R.id.containerMain, SubFragment1()) // containerMain SubFragment2 출력
                        .addToBackStack(FragmentName.SUB_FRAGMENT1.name) // 백스택에 추가
                        .commit() // 실행
                }
            }

            // 총점 및 평균
            buttonMainTotalAverage.setOnClickListener {
                parentFragmentManager.apply {
                    beginTransaction() // 트랜잭션 생성
                        .replace(R.id.containerMain, SubFragment2()) // containerMain SubFragment2 출력
                        .addToBackStack(FragmentName.SUB_FRAGMENT2.name) // 백스택에 추가
                        .commit() // 실행
                }
            }
        }

    }

    fun settingView(){

    }
}