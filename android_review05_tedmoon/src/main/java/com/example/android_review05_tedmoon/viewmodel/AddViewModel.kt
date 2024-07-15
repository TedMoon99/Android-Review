package com.example.android_review05_tedmoon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    // View 요소 설정
    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()
    val studentKorean = MutableLiveData<String>()
    val studentEnglish = MutableLiveData<String>()
    val studnetMath = MutableLiveData<String>()

    // 정보를 DB에 저장한다

    // 정보를 DB에서 불러온다

    // 입력 요소 초기화


}