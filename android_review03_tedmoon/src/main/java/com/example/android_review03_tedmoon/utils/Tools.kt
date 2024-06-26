package com.example.android_review03_tedmoon.utils

import com.example.android_review03_tedmoon.model.ScoreInfo

class Tools {
    companion object{
        // 학생 정보 객체를 담을 리스트
        val scoreList = mutableListOf<ScoreInfo>()

    }
}

enum class FragmentName(name: String){
    MAIN_FRAGMENT("MainFragment"),
    SUB_FRAGMENT1("SubFragment1"),
    SUB_FRAGMENT2("SubFragment2"),

}
