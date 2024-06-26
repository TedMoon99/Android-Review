package com.example.android_review03_kshn379

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_review03_kshn379.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    // binding 관련 초기화를 해준다
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    // 일반 함수를 호출해준다
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View
        settingView()
        // Event
        settingEvent()
    }

    // View 설정
    fun settingView(){


    }
    // Event 설정
    fun settingEvent(){
        binding.apply {
            btnMainInfo.setOnClickListener {
                // 화면 전환
                parentFragmentManager
                    .beginTransaction() // 트랜잭션 생성
                    .replace(R.id.containerMain, SubFragment())
                    .addToBackStack("SubFragment")
                    .commit() // 실행
            }
            btnMainScore.setOnClickListener {
                // 화면 전환
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, ThirdFragment())
                    .addToBackStack("ThirdFragment")
                    .commit()
            }
        }

    }

}
