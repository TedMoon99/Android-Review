package com.example.android_review04_tedmoon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review04_tedmoon.dao.ScoreDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class ShowViewModel: ViewModel() {
    // 이름
    val studentName = MutableLiveData<String>()
    // 학년
    val studentGrade = MutableLiveData<String>()
    // 각 점수
    val studentKorean = MutableLiveData<String>()
    val studentEnglish = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()
    // 전체 총점 및 평균
    val studentTotal = MutableLiveData<String>()
    val studentAverage = MutableLiveData<String>()

    // DB에서 데이터를 가져온다
    fun gettingData(position: Int){
        viewModelScope.launch {
            try {
                // position을 이용하여 학생 정보를 반환한다
                val scoreInfo = withContext(Dispatchers.IO){ ScoreDao.gettingStudentInfoByStudentIdx(position)}

                // 불러온 데이터를 연결해준다
                if (scoreInfo != null){
                    // 학생이 받은 총점 계산
                    val total = scoreInfo.korean + scoreInfo.english + scoreInfo.math
                    // 학생 성적의 평균 계산
                    val average = round(total / 3 * 100.0) / 100.0
                    studentName.value = "이름 : ${scoreInfo.name}"
                    studentGrade.value = "학년 : ${scoreInfo.grade}학년"
                    studentKorean.value = "국어 점수 : ${scoreInfo.korean}점"
                    studentEnglish.value = "영어 점수 : ${scoreInfo.english}점"
                    studentMath.value = "수학 점수 : ${scoreInfo.math}점"
                    studentTotal.value = "총점 : ${total}점"
                    studentAverage.value = "평균 : ${average}점"
                }
            } catch (e: Exception){
                Log.e("ShowViewModel", "데이터 조회 실패 : ${e.message}")
            }
        }
    }
    // 입력요소 초기화
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