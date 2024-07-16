package com.example.android_review05_kshn379

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class InfoViewModel: ViewModel() {
    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()

    val studentKor = MutableLiveData<String>()
    val studentEng = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()

    val studentTotal = MutableLiveData<String>()
    val studentAverage = MutableLiveData<String>()

    // DB 연동
    fun getData(position: Int) {
        viewModelScope.launch {
            try {
                // 학생 정보 반환
                val manageInfo = withContext(Dispatchers.IO){AddDao.getManageInfoByStudentIdx(position)}

                // 연동 데이터 연결
                if (manageInfo != null){
                    // 총점 계산
                    val total = manageInfo.studentKor + manageInfo.studentEng + manageInfo.studentMath
                    // 평균 계산
                    val average = round(total / 3 *100.0) / 100.0
                    studentName.value = "이름 : ${manageInfo.studentName}"
                    studentGrade.value = "학년 : ${manageInfo.studentGrade}학년"
                    studentKor.value = "국어 점수 : ${manageInfo.studentKor}점"
                    studentEng.value = "영어 점수 : ${manageInfo.studentEng}점"
                    studentMath.value = "수학 점수 : ${manageInfo.studentMath}점"
                    studentTotal.value = "총점 : ${total}점"
                    studentAverage.value = "평균 : ${average}점"
                }
            }catch (e: Exception){
                Log.e("InfoViewModel", "Data Load Failed : ${e.message}")
            }
        }
    }

    // 입력 요소 초기화
    fun dataInit(){
        studentName.value = ""
        studentGrade.value =""
        studentKor.value =""
        studentEng.value =""
        studentMath.value =""
        studentTotal.value =""
        studentAverage.value =""
    }
}