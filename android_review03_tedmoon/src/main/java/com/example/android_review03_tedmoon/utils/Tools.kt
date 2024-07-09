package com.example.android_review03_tedmoon.utils

import android.util.Log
import com.example.android_review03_tedmoon.model.ScoreInfo
import kotlin.math.roundToInt

class Tools {
    companion object{
        var num = 0 // 전체 데이터 갯수
        var total = 0.0  // 전체 총점
        var average = 0.0 // 전체 평균

        // 전체 총점을 구하는 함수
        fun gettingTotal(korean: Double, english: Double, math: Double){
            // 전체 데이터 갯수 + 1
            num += 1
            val sum = korean + english + math
            total += sum // 총점 저장
            Log.d("test1234", "${total}")
        }

        // 전체 평균을 구하는 함수
        fun gettingAverage(){
            // 소수점 둘째 자리까지 출력
            val avg = ((total / num) * 100.0).roundToInt() / 100.0
            average = avg
            Log.d("test1234", "${average}")
        }
    }
}

enum class FragmentName(name: String){
    SUB_FRAGMENT1("SubFragment1"),
    SUB_FRAGMENT2("SubFragment2"),
    SUB_FRAGMENT3("SubFragment3"),

}
