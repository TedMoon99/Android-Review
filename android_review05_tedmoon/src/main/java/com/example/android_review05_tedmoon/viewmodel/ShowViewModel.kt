package com.example.android_review05_tedmoon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review05_tedmoon.dao.ScoreDao
import com.example.android_review05_tedmoon.dao.TotalDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowViewModel: ViewModel() {
    // studentIdx 설정
    val studentIdx = MutableLiveData<Int>()
    // View 요소 설정
    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()
    val studentKorean = MutableLiveData<String>()
    val studentEnglish = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()
    val studentTotal = MutableLiveData<String>()
    val studentAverage = MutableLiveData<String>()
    // DB에서 정보를 불러온다
    fun getData(studentIdx: Int){
        viewModelScope.launch {
            try {
                // 데이터 조회
                val scoreData = withContext(Dispatchers.IO){ ScoreDao.getStudentDataByIdx(studentIdx) }
                val TotalData = withContext(Dispatchers.IO){ TotalDao.gettingTotalData(studentIdx) }
                // 데이터 설정
                studentName.value = "이름 : ${scoreData?.name}"
                studentGrade.value = "학년 : ${scoreData?.grade}"
                studentKorean.value = "국어 점수 : ${scoreData?.korean}점"
                studentEnglish.value = "영어 점수 : ${scoreData?.english}점"
                studentMath.value = "수학 점수 : ${scoreData?.math}점"
                studentTotal.value = "총점 : ${TotalData?.wholeTotal}점"
                studentAverage.value = "평균 : ${TotalData?.average}점"
                Log.d("ShowViewModel", "데이터 조회 확인 ScoreData : $scoreData")
                Log.d("ShowViewModel", "데이터 조회 확인 TotalData : $TotalData")
            } catch (e: Exception){
                Log.e("ShowViewModel", "데이터 조회 실패 : ${e.message}")
            }
        }
    }
    // 입력 요소 초기화
    fun initData(){
        studentName.value = ""
        studentGrade.value = ""
        studentKorean.value = ""
        studentEnglish.value = ""
        studentMath.value = ""
        studentTotal.value = ""
        studentAverage.value = ""
    }


}