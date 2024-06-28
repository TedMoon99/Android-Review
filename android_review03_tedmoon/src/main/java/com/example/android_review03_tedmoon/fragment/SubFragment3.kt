package com.example.android_review03_tedmoon.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_review03_tedmoon.R
import com.example.android_review03_tedmoon.databinding.FragmentSub3Binding
import com.example.android_review03_tedmoon.model.ScoreInfo

class SubFragment3 : Fragment() {

    private lateinit var binding: FragmentSub3Binding
    var data: ScoreInfo? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSub3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 필요한 정보를 읽어온다
        initData()
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    // 필요한 정보를 읽어온다
    fun initData(){
        // argumenets에서 bundle을 받아온다
        val bundle = arguments
        // Bundle 객체에서 key값을 이용하여 ArrayList를 뽑아서 dataList에 담아준다
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // API 33이상인 경우 사용하는 코드
            bundle?.getParcelable("positionData", ScoreInfo::class.java)
        } else { // API 33 미만에서 사용하는 코드
            @Suppress("DEPRECATION")
            bundle?.getParcelable("positionData")
        }
        // Data 전송 test
        Log.d("test1234", "데이터 수신 확인 : $data")

    }

    // View 설정
    fun settingView(){
        binding.apply {
            // 이름 설정
            textViewSub3Name.text = "이름 : ${data?.name}"
            // 학년 설정
            textViewSub3Grade.text = "학년 : ${data?.grade}학년"
            // 국어
            textViewSub3Korean.text = "국어 점수 : ${data?.korean}점"
            // 영어
            textViewSub3English.text = "영어 점수 : ${data?.english}점"
            // 수학
            textViewSub3Math.text = "수학 점수 : ${data?.math}점"
            // 총점
            textViewSub3Total.text = "총점 test"
            // 평균
            textViewSub3Average.text = "평균 test"
        }

    }
    // Event 설정
    fun settingEvent(){

    }
}