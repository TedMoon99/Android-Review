package com.example.android_review04_kshn3792

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}