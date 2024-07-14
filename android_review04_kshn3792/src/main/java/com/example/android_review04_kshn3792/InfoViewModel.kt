package com.example.android_review04_kshn3792

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class InfoViewModel: ViewModel() {
    // 이름
    val studentName = MutableLiveData<String>()
    // 학년
    val studentGrade = MutableLiveData<String>()

    // 국어 점수
    val studentKor = MutableLiveData<String>()
    // 영어 점수
    val studentEng = MutableLiveData<String>()
    // 수학 점수
    val studentMath = MutableLiveData<String>()

    // 총점
    val studentTotal = MutableLiveData<String>()
    // 평균
    val studentAvr = MutableLiveData<String>()

    // DB에서 데이터를 가져온다
    fun getData(position: Int) {
        viewModelScope.launch {
            try {
                // position을 이용하여 학생 정보 반환한다
                val studentInfo = withContext(Dispatchers.IO){ AddDao.gettingStudentInfoByStudentIdx(position)}

                // 불러온 데이터 연결
                if (studentInfo != null) {
                    // 학생이 받은 총점 계산
                    val total = studentInfo.studentKor + studentInfo.studentEng + studentInfo.studentMath
                    // 학생이 받은 평균 계산
                    val average = round(total / 3 * 100.0) / 100.0
                    studentName.value = "이름 : ${studentInfo.studentName}"
                    studentGrade.value = "학년 : ${studentInfo.studentGrade}학년"
                    studentKor.value = "국어 점수 : ${studentInfo.studentKor}점"
                    studentEng.value = "영어 점수 : ${studentInfo.studentEng}점"
                    studentMath.value = "수학 점수 : ${studentInfo.studentMath}점"
                    studentTotal.value = "총점 : ${total}점"
                    studentAvr.value = "평균 : ${average}점"
                    Log.d("InfoViewModel", "데이터 설정 완료: ${studentInfo.studentName}")
                } else {
                    Log.d("InfoViewModel", "Error")
                }
            } catch (e: Exception){
                Log.e("InfoViewModel", "데이터 조회 실패 : ${e.message}")
            }
        }
    }

    // 입력 요소 초기화
    fun dataInit(){
        studentName.value =""
        studentGrade.value =""
        studentKor.value =""
        studentEng.value =""
        studentMath.value =""
        studentTotal.value =""
        studentAvr.value =""
    }
}