package com.example.android_review04_tedmoon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review04_tedmoon.dao.TotalDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoViewModel: ViewModel() {
    // 총점
    val totalKorean = MutableLiveData<String>()
    val totalEnglish = MutableLiveData<String>()
    val totalMath = MutableLiveData<String>()
    // 평균
    val averageKorean = MutableLiveData<String>()
    val averageEnglish = MutableLiveData<String>()
    val averageMath = MutableLiveData<String>()

    // 전체 총점
    val wholeTotal = MutableLiveData<String>()
    // 전체 평균
    val wholeAverage = MutableLiveData<String>()


    // DB에 접근해서 필요한 데이터를 불러온다
    fun getData(){
        viewModelScope.launch {
            try {
                // DB에서 데이터를 받아옴
                val totalInfo = withContext(Dispatchers.IO){ TotalDao.gettingTotalData() }

                // 받아온 데이터를 연결해줌
                // 각 총점
                totalKorean.value = "국어 총점 : ${totalInfo.koreanTotal}점"
                totalEnglish.value = "영어 총점 : ${totalInfo.englishTotal}점"
                totalMath.value = "수학 총점 : ${totalInfo.mathTotal}점"
                // 각 평균
                averageKorean.value = "국어 평균 : ${totalInfo.koreanAverage}점"
                averageEnglish.value = "영어 평균 : ${totalInfo.englishAverage}점"
                averageMath.value = "수학 평균 : ${totalInfo.mathAverage}점"
                // 전체 총점
                wholeTotal.value = "전체 총점 : ${totalInfo.wholeTotal}점"
                // 전체 평균
                wholeAverage.value = "전체 평균 : ${totalInfo.wholeAverage}점"
            } catch (e: Exception){
                Log.d("InfoViewModel", "데이터 설정 실패 : ${e.message}")
            }
        }
    }
}