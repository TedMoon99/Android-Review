package com.example.android_review05_tedmoon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowViewModel: ViewModel() {
    // View 요소 설정
    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()
    val studentKorean = MutableLiveData<String>()
    val studentEnglish = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()

    // DB에서 정보를 불러온다

    // 입력 요소 초기화


}