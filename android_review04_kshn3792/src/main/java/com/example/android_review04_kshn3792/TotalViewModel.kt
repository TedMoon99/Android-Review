package com.example.android_review04_kshn3792

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TotalViewModel: ViewModel() {
    // 국어 총점
    val totalKor = MutableLiveData<String>()
    // 국어 평균
    val averageKor = MutableLiveData<String>()
    // 영어 총점
    val totalEng = MutableLiveData<String>()
    // 영어 평균
    val averageEng = MutableLiveData<String>()
    // 수학 총점
    val totalMath = MutableLiveData<String>()
    // 수학 평균
    val averageMath = MutableLiveData<String>()

    // 전체 총점
    val allTotal = MutableLiveData<String>()
    // 전체 평균
    val allAverage = MutableLiveData<String>()

    // DB에 접근해서 필요한 데이터 불러오기
    fun getData(){
        viewModelScope.launch {
            try {
                // DB에서 데이터 불러오기
                val totalInfo = withContext(Dispatchers.IO){ TotalDao.gettingTotalData()}

                // 받아온 데이터 연결
                // 국어 총점
                totalKor.value = "국어 총점 : ${totalInfo.korTotal}점"
                // 국어 평균
                averageKor.value = "국어 평균 : ${totalInfo.korAverage}점"
                // 영어 총점
                totalEng.value = "영어 총점 : ${totalInfo.engTotal}점"
                // 영어 평균
                averageEng.value = "영어 평균 : ${totalInfo.engAverage}점"
                // 수학 총점
                totalMath.value = "수학 총점 : ${totalInfo.mathTotal}점"
                // 수학 평균
                averageMath.value = "수학 평균 : ${totalInfo.mathAverage}점"

                // 전체 총점
                allTotal.value = "전체 총점 : ${totalInfo.allTotal}점"
                // 전체 평균
                allAverage.value = "전체 평균 : ${totalInfo.allAverage}점"
            } catch (e: Exception) {
                Log.d("TotalViewModel", "데이터 설정 실패 : ${e.message}")
            }
        }
    }
}